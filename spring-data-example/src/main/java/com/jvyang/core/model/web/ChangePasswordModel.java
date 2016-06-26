package com.jvyang.core.model.web;

import java.io.Serializable;

public class ChangePasswordModel implements Serializable
{
  private static final long serialVersionUID = 4433567498156948578L;
  String oldPassword;
  String newPassword;
  String confirmPassword;

  public String getOldPassword()
  {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword)
  {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword()
  {
    return newPassword;
  }

  public void setNewPassword(String newPassword)
  {
    this.newPassword = newPassword;
  }

  public String getConfirmPassword()
  {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword)
  {
    this.confirmPassword = confirmPassword;
  }

}
