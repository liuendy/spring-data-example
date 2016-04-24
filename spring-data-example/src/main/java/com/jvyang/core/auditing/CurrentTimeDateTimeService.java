package com.jvyang.core.auditing;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class CurrentTimeDateTimeService implements DateTimeService
{

  @Override
  public Date getCurrentDateAndTime()
  {
    return new Date();
  }
}