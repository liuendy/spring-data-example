package com.jvyang.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jvyang.core.jpa.repositories.UserRepository;
import com.jvyang.core.model.QUser;
import com.jvyang.core.model.User;

@Service
public class UserServiceImpl implements UserService
{
  @Autowired UserRepository userRepository;

  @Override
  public User createUser(User user)
  {
    return userRepository.save(user);
  }

  @Override
  public User getUser(long id)
  {
    return userRepository.findOne(QUser.user.id.eq(id));
  }

  @Override
  public User getUser(String username)
  {
    return userRepository.findOne(QUser.user.username.eq(username));
  }

  @Override
  @Transactional
  public User updateUser(User user)
  {
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User deactiveUser(User user)
  {
    user.setIsAccountNotExpired(false);
    userRepository.save(user);
    return null;
  }

}
