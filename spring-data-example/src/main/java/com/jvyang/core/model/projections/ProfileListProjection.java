package com.jvyang.core.model.projections;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.jvyang.core.model.Profile;

@Projection(name = "listing", types = { Profile.class })
public interface ProfileListProjection
{
  String getName();

  String getDescription();

  Date getCreatedBy();

  boolean getIsActive();
}
