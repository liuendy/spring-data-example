package com.jvyang.core.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProperties
{
  public static final String TO_STRING_FORMAT = "TokenProperties [secretKey=%s, " + "expirationTime=%s, issuer=%s]";

  @Value("${jwt.secretkey}") String secretKey;
  @Value("${jwt.expirationTime}") Integer expirationTime;
  @Value("${jwt.issuer}") String issuer;

  public String getSecretKey()
  {
    return secretKey;
  }

  public Integer getExpirationTime()
  {
    return expirationTime;
  }

  public String getIssuer()
  {
    return issuer;
  }

  @Override
  public String toString()
  {
    return String.format(TO_STRING_FORMAT, secretKey, expirationTime, issuer);
  }
}
