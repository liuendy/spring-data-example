package com.jvyang.core.service;

import java.util.List;

import com.jvyang.core.model.Profile;

public interface ProfileService
{
  public Profile getProfile(long id);

  public List<Profile> getProfiles();

  public List<Profile> getProfiles(boolean isActive);

  public Profile updateProfile(Profile profile);

}
