package com.jvyang.core.service;

import com.jvyang.core.model.User;

public interface ChangePasswordService
{
  public boolean changePassword(User user, String oldPassword, String newPassword);
}
