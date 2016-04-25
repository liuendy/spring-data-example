package com.jvyang.core.web.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jvyang.core.model.FullName;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse extends Response
{
  private static final long serialVersionUID = -3492065846061432485L;

  public LoginResponse(boolean success)
  {
    super(success);
  }

  public LoginResponse(boolean success, String errorCode)
  {
    super(success, errorCode);
  }

  protected String token;
  protected String username;
  protected FullName fullName;
  protected List<String> profiles;
  protected List<String> accesses;

  public String getToken()
  {
    return token;
  }

  public void setToken(String token)
  {
    this.token = token;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getFullName()
  {
    if (fullName != null)
    {
      return fullName.getWholeName();
    }
    else
    {
      return null;
    }
  }

  public void setFullName(FullName fullName)
  {
    this.fullName = fullName;
  }

  public List<String> getProfiles()
  {
    return profiles;
  }

  public void setProfiles(List<String> profiles)
  {
    this.profiles = profiles;
  }

  public List<String> getAccesses()
  {
    return accesses;
  }

  public void setAccesses(List<String> accesses)
  {
    this.accesses = accesses;
  }

}