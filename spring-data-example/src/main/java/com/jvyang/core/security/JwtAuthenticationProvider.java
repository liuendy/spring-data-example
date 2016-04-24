package com.jvyang.core.security;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.jvyang.core.config.properties.TokenProperties;
import com.jvyang.core.jpa.repositories.TokenRepository;
import com.jvyang.core.model.JwtToken;
import com.jvyang.core.model.SimpleJwtToken;
import com.jvyang.core.model.exceptions.InvalidSignatureException;
import com.jvyang.core.model.exceptions.InvalidTokenException;
import com.jvyang.core.model.exceptions.TokenExpiredException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider
{
  protected final Logger L = LogManager.getLogger(getClass());

  @Autowired TokenProperties tokenProperties;
  @Autowired TokenRepository tokenRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException
  {
    JwtToken jwtToken = (JwtToken) authentication;

    try
    {
      JWSVerifier verifier = new MACVerifier(tokenProperties.getSecretKey());

      Date referenceTime = new Date();
      JWTClaimsSet claims = jwtToken.getClaims();

      if (!tokenProperties.getIssuer().equals(claims.getIssuer()))
      {
        throw new InvalidTokenException("Invalid issuer");
      }

      Date expirationTime = claims.getExpirationTime();
      if (expirationTime == null || expirationTime.before(referenceTime))
      {
        throw new TokenExpiredException("Token is expired");
      }

      Date notBeforeTime = claims.getNotBeforeTime();
      if (notBeforeTime == null || notBeforeTime.after(referenceTime))
      {
        throw new InvalidTokenException("Not before time is after sysdate");
      }

      if (jwtToken.getSignedToken().verify(verifier))
      {
        jwtToken.setAuthenticated(true);
      }
      else
      {
        throw new InvalidSignatureException("Signature validation failed");
      }

      SimpleJwtToken retrievedToken = tokenRepository.findFirstBySubjectOrderByIssuedAtDesc(claims.getSubject());

      if (retrievedToken.getId() != Long.valueOf(claims.getJWTID()))
      {
        throw new InvalidTokenException("Token is not validated. Wrong JWT ID");
      }

      if (retrievedToken.getIssuedAt().equals(claims.getIssueTime()))
      {
        throw new InvalidTokenException("Token is not validated. Wrong Issued At");
      }

      return jwtToken;
    }
    catch (JOSEException e)
    {
      throw new InvalidSignatureException("Signature validation failed");
    }

  }

  @Override
  public boolean supports(Class<?> authentication)
  {
    return true;
  }

}
