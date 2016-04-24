package com.jvyang.core.auditing;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

@Component("dateTimeProvider")
public class AuditingDateTimeProvider implements DateTimeProvider
{
  private final DateTimeService dateTimeService;

  @Autowired
  public AuditingDateTimeProvider(DateTimeService dateTimeService)
  {
    this.dateTimeService = dateTimeService;
  }

  @Override
  public Calendar getNow()
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(dateTimeService.getCurrentDateAndTime());

    return cal;
  }
}