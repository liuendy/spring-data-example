package com.jvyang.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.domain.AuditorAware;

import com.jvyang.core.auditing.CurrentTimeDateTimeService;
import com.jvyang.core.auditing.DateTimeService;
import com.jvyang.core.auditing.UsernameAuditorAware;

@Configuration
@ComponentScan("com.jvyang.core")
@Import({ SecurityContext.class, WebMvcContext.class, PersistenceContext.class })
@PropertySource("classpath:application.properties")
public class ApplicationContext
{
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
  {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  DateTimeService currentTimeDateTimeService()
  {
    return new CurrentTimeDateTimeService();
  }

  @Bean
  AuditorAware<String> auditorProvider()
  {
    return new UsernameAuditorAware();
  }
}
