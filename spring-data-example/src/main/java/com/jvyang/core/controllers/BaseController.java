package com.jvyang.core.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jvyang.core.model.JwtToken;
import com.jvyang.core.model.User;
import com.jvyang.core.service.UserService;

public abstract class BaseController
{
  protected final Logger L = LogManager.getLogger(getClass());

  @Autowired UserService userService;

  protected User getUser()
  {
    Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
    final String username = ((JwtToken) authentication).getClaims().getSubject();

    return userService.getUser(username);
  }
}
