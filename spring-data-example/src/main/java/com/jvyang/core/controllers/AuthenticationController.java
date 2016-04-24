package com.jvyang.core.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jvyang.core.auth.UserAuthenticationService;
import com.jvyang.core.model.Access;
import com.jvyang.core.model.LoginModel;
import com.jvyang.core.model.User;
import com.jvyang.core.security.TokenCreationService;
import com.jvyang.core.security.filters.JwtFilter;
import com.jvyang.core.web.responses.LoginResponse;
import com.nimbusds.jwt.SignedJWT;

@CrossOrigin
@RestController
public class AuthenticationController extends BaseController
{
  @Autowired UserAuthenticationService authService;
  @Autowired TokenCreationService tokenService;

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = "/api/login", method = RequestMethod.GET)
  public ResponseEntity<Object> loginCheck()
  {
    return new ResponseEntity<Object>(HttpStatus.OK);
  }

  @RequestMapping(value = "/api/login", method = RequestMethod.POST)
  public ResponseEntity<Object> login(@RequestBody LoginModel model, HttpServletResponse response)
      throws JsonProcessingException
  {
    User user = null;
    try
    {
      user = authService.authenticate(new User(model.getUsername(), model.getPassword()));
    }
    catch (UsernameNotFoundException unfe)
    {
      return ResponseEntity.ok(new LoginResponse(false, "Username not found."));
    }
    catch (BadCredentialsException bce)
    {
      return ResponseEntity.ok(new LoginResponse(false, "Wrong username and/or password."));
    }

    SignedJWT signedJWT = tokenService.createToken(user);

    // If signedJWT is null, something went wrong during token creation.
    if (signedJWT == null)
    {
      return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    LoginResponse lr = new LoginResponse(true);
    lr.setUsername(user.getUsername());
    lr.setFullName(user.getFullName());
    lr.setAccesses(convertAuthoritesToArray(user.getAuthorities()));
    lr.setProfiles(user.getUserProfiles().stream().map(profile -> profile.getName()).collect(Collectors.toList()));

    Cookie cookie = new Cookie(JwtFilter.AUTH_COOKIE_KEY, signedJWT.serialize());
    cookie.setHttpOnly(true);
    response.addCookie(cookie);

    return ResponseEntity.ok(lr);
  }

  private List<String> convertAuthoritesToArray(List<Access> accesses)
  {
    return accesses.stream().map(x -> new String(x.getCode())).collect(Collectors.toList());
  }

  @RequestMapping(value = "/403")
  public ResponseEntity<Object> accessDenied()
  {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("message", "Access denied");

    return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
  }
}