package com.jvyang.core.security.filters;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.jvyang.core.model.JwtToken;
import com.nimbusds.jwt.SignedJWT;

public class JwtFilter extends GenericFilterBean
{
  protected final Logger L = LogManager.getLogger(getClass());

  AuthenticationManager authenticationManager;

  public JwtFilter(AuthenticationManager authenticationManager)
  {
    this.authenticationManager = authenticationManager;
  }

  public static final String AUTH_COOKIE_KEY = "Authentication";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException
  {
    L.debug("JwtFilter.doFilter");
    L.debug("JwtFilter.authenticationManager" + authenticationManager);

    HttpServletRequest req = (HttpServletRequest) request;

    String stringToken = "";
    Cookie[] cookies = req.getCookies();

    if (cookies != null)
    {
      for (Cookie cookie : cookies)
      {
        if (cookie.getName().equalsIgnoreCase(AUTH_COOKIE_KEY))
        {
          stringToken = cookie.getValue();
          break;
        }
      }
    }

    L.debug("JwtFilter.doFilter.stringToken: " + stringToken);

    if (!Strings.isBlank(stringToken))
    {
      try
      {
        SignedJWT sjwt = SignedJWT.parse(stringToken);
        JwtToken token = new JwtToken(sjwt);
        Authentication auth = authenticationManager.authenticate((Authentication) token);
        SecurityContextHolder.getContext().setAuthentication(auth);

      }
      catch (ParseException e)
      {
        L.catching(Level.DEBUG, e);
      }
      catch (AuthenticationException e)
      {
        if (!L.isDebugEnabled())
        {
          L.info("Token is invalid.");
        }
        L.catching(Level.DEBUG, e);
      }
    }
    else
    {
      L.info("No token found.");
    }

    chain.doFilter(request, response);
  }

}