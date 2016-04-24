package com.jvyang.core.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint
{

  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authenticationException) throws IOException, ServletException
  {
    response.setContentType("application/json");
    response.setStatus(HttpStatus.UNAUTHORIZED.value());

    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("success", false);
    responseMap.put("message", "Access denied.");

    ObjectMapper mapper = new ObjectMapper();
    String responseBody = mapper.writer().writeValueAsString(responseMap);

    response.getOutputStream().println(responseBody);
  }
}