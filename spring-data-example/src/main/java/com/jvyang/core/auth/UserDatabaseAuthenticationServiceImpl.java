package com.jvyang.core.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.digest.StandardStringDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jvyang.core.model.User;
import com.jvyang.core.service.UserService;

@Service
@Transactional
public class UserDatabaseAuthenticationServiceImpl implements UserAuthenticationService
{
  protected final Logger L = LogManager.getLogger(getClass());

  @Autowired UserService userService;
  @Autowired StandardStringDigester stringDigester;

  @Override
  public User authenticate(User user) throws AuthenticationException
  {
    User authUser = userService.getUser(user.getUsername());

    if (authUser == null)
    {
      throw new UsernameNotFoundException("User (" + user.getUsername() + ") does not exist in the system.");
    }

    if (stringDigester.matches(user.getPassword(), authUser.getPassword()))
    {
      return authUser;
    }

    BadCredentialsException e = new BadCredentialsException("Wrong username and/or password.");
    L.error(e.getMessage());

    throw e;
  }

}