package com.jvyang.core.service;

import com.jvyang.core.model.User;

public interface UserService
{
  public User createUser(User user);
  
  public User getUser(long id);
  
  public User getUser(String username);
  
  public User updateUser(User user);
  
  public User deactiveUser(User user);
}
