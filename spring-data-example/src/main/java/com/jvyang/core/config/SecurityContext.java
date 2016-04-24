package com.jvyang.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.jvyang.core.security.JwtAuthenticationProvider;
import com.jvyang.core.security.filters.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityContext extends WebSecurityConfigurerAdapter
{
  protected final Logger L = LogManager.getLogger(getClass());

  @Autowired JwtAuthenticationProvider jwtAuthenticationProvider;
  @Autowired AuthenticationEntryPoint authenticationEntryPoint;

  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception
  {
    return super.authenticationManager();
  }

  @Override
  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    auth.authenticationProvider(jwtAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http.csrf().disable()
    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    .and()
    .addFilterAfter(new JwtFilter(authenticationManagerBean()), AnonymousAuthenticationFilter.class)
    .addFilterBefore(corsFilter(), ChannelProcessingFilter.class)
    .authorizeRequests().antMatchers(HttpMethod.POST, "/api/login").permitAll()
    .and()
    .authorizeRequests().antMatchers("/api/**").authenticated()
    .and()
    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    // http.authorizeRequests().antMatchers("/api/users/**").hasAuthority("ROLE_ADMIN");
    // http.authorizeRequests().antMatchers("/api/profiles/**").hasAuthority("ROLE_ADMIN");
  }

  @Bean
  CorsFilter corsFilter()
  {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("DELETE");
    config.addAllowedMethod("PATCH");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}