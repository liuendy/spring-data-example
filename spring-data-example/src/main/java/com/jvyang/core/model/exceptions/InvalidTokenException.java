package com.jvyang.core.model.exceptions;

public class InvalidTokenException extends TokenException
{
  private static final long serialVersionUID = -1380030677864638146L;

  public InvalidTokenException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public InvalidTokenException(String message)
  {
    super(message);
  }
}
