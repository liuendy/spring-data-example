package com.jvyang.core.security.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SecurityUtils
{
  public boolean isRestRequest()
  {
    HttpServletRequest r = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    return Pattern.matches("^/api/", UrlUtils.buildRequestUrl(r));
  }
}