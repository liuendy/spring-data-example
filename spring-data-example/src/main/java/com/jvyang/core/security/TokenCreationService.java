package com.jvyang.core.security;

import com.jvyang.core.model.User;
import com.nimbusds.jwt.SignedJWT;

public interface TokenCreationService
{
  public SignedJWT createToken(final User user);
}