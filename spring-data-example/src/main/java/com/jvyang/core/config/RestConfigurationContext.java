package com.jvyang.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.jvyang.core.model.Access;
import com.jvyang.core.model.Profile;
import com.jvyang.core.model.User;

@Configuration
public class RestConfigurationContext
{
  @Bean
  public RepositoryRestConfigurer repositoryRestConfigurer()
  {
    return new RepositoryRestConfigurerAdapter()
    {

      @Override
      public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config)
      {
        config.setBasePath("/api");
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Profile.class);
        config.exposeIdsFor(Access.class);
      }
    };
  }
}
