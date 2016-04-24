package com.jvyang.core.model;

import java.io.Serializable;

public class LoginModel implements Serializable
{
  private static final long serialVersionUID = -4410670961177626430L;

  String username;
  String password;

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

}