package com.jvyang.core.model.exceptions;

import org.springframework.security.core.AuthenticationException;

public class DatabaseAuthenticationException extends AuthenticationException
{
  private static final long serialVersionUID = -5065539974590321054L;

  public DatabaseAuthenticationException(String msg)
  {
    super(msg);
  }

  public DatabaseAuthenticationException(String msg, Throwable t)
  {
    super(msg, t);
  }

}
