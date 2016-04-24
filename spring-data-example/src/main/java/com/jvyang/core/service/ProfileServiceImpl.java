package com.jvyang.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.jvyang.core.jpa.repositories.ProfileRepository;
import com.jvyang.core.model.Profile;
import com.jvyang.core.model.QProfile;

@Service
public class ProfileServiceImpl implements ProfileService
{

  @Autowired ProfileRepository profileRepository;

  @Override
  public Profile getProfile(long id)
  {
    return profileRepository.findOne(QProfile.profile.id.eq(id));
  }

  @Override
  public List<Profile> getProfiles()
  {
    return profileRepository.findAll();
  }

  @Override
  public List<Profile> getProfiles(boolean isActive)
  {
    return Lists.newArrayList(profileRepository.findAll(QProfile.profile.isActive.eq(isActive)));
  }

  @Override
  @Transactional
  public Profile updateProfile(Profile profile)
  {
    return profileRepository.save(profile);
  }

}
