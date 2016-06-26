package com.jvyang.core.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableWebMvc
@Import(RepositoryRestMvcConfiguration.class)
class WebMvcContext extends WebMvcConfigurerAdapter
{

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
  {
    configurer.enable();
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
  {
    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.registerModule(new JavaTimeModule());

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(objectMapper);

    converters.add(converter);
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry)
  {
    registry.jsp("/WEB-INF/jsp/", ".jsp");
  }

  @Bean
  public SimpleMappingExceptionResolver simpleMappingExceptionResolver()
  {
    SimpleMappingExceptionResolver b = new SimpleMappingExceptionResolver();
    b.setExcludedExceptions(AccessDeniedException.class);
    return b;
  }

  @Bean(name = "repositoryRestConfigurer")
  public RepositoryRestConfigurer repositoryRestConfigurer()
  {
    return new RepositoryRestConfigurerAdapter()
    {

      @Override
      public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config)
      {
        config.setBasePath("/api");
        config.setDefaultMediaType(MediaType.APPLICATION_JSON_UTF8);
      }
    };
  }
}