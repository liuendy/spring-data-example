package com.jvyang.core.service;

import org.jasypt.digest.StandardStringDigester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvyang.core.model.User;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService
{
  @Autowired UserService userService;
  @Autowired StandardStringDigester stringDigester;

  public boolean changePassword(User user, String oldPassword, String newPassword)
  {
    if (oldPassword.equals(newPassword) || !stringDigester.matches(oldPassword, user.getPassword()))
    {
      return false;
    }
    
    user.setPassword(stringDigester.digest(newPassword));
    userService.updateUser(user);

    return true;
  }
}
