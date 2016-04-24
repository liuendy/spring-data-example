package com.jvyang.core.model.exceptions;

public class TokenExpiredException extends TokenException
{
  private static final long serialVersionUID = -3332873965950401630L;

  public TokenExpiredException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public TokenExpiredException(String message)
  {
    super(message);
  }
}
