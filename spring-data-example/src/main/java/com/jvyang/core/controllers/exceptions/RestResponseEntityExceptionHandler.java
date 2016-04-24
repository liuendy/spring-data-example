package com.jvyang.core.controllers.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = { "com.jvyang.core.controllers" })
class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{

  @ExceptionHandler({ AccessDeniedException.class })
  public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e)
  {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("message", "Access denied.");

    return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
  }
}