package com.jvyang.core.web.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public abstract class Response implements Serializable
{
  private static final long serialVersionUID = -6823707508031166736L;

  protected boolean success;
  protected String responseCode;

  public Response(boolean success)
  {
    this.success = success;
  }

  public Response(boolean success, String responseCode)
  {
    this.success = success;
    this.responseCode = responseCode;
  }

  public boolean isSuccess()
  {
    return success;
  }

  public void setSuccess(boolean success)
  {
    this.success = success;
  }

  public String getResponseCode()
  {
    return responseCode;
  }

  public void setResponseCode(String responseCode)
  {
    this.responseCode = responseCode;
  }

}
