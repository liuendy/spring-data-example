package com.jvyang.core.model.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenException extends AuthenticationException
{
  private static final long serialVersionUID = -398628598676198877L;

  public TokenException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public TokenException(String message)
  {
    super(message);
  }
}
