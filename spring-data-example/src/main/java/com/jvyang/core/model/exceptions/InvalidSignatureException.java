package com.jvyang.core.model.exceptions;

public class InvalidSignatureException extends TokenException
{
  private static final long serialVersionUID = -1380030677864638146L;

  public InvalidSignatureException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public InvalidSignatureException(String message)
  {
    super(message);
  }
}
