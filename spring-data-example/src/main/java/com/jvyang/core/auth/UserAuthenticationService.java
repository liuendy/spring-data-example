package com.jvyang.core.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jvyang.core.model.User;

public interface UserAuthenticationService
{
  public User authenticate(User user)
      throws AuthenticationException, UsernameNotFoundException, BadCredentialsException;
}
