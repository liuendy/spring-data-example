package com.jvyang.core.model.projections;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.jvyang.core.model.User;

@Projection(name = "listing", types = { User.class })
public interface UserListProjection
{
  String getUsername();

  @Value("#{target.fullName.getWholeName()}")
  String getFullName();

  @Value("#{target.fullName.getGivenName()}")
  String getFirstName();

  @Value("#{target.fullName.getLastName()}")
  String getLastName();

  String getDelimitedProfiles();

  Date getCreatedBy();

  boolean getIsActive();
}
