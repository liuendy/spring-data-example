package com.jvyang.core.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvyang.core.config.properties.TokenProperties;
import com.jvyang.core.jpa.repositories.TokenRepository;
import com.jvyang.core.model.Access;
import com.jvyang.core.model.SimpleJwtToken;
import com.jvyang.core.model.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class TokenService implements TokenCreationService
{
  private final Logger L = LogManager.getLogger(getClass());

  @Autowired TokenProperties tokenProperties;
  @Autowired TokenRepository tokenRepository;

  @Override
  public SignedJWT createToken(final User user)
  {
    try
    {
      SimpleJwtToken token = createAndStoreToken(user.getUsername());

      JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
      builder.jwtID(token.getId().toString());
      builder.subject(token.getSubject());
      builder.issuer(tokenProperties.getIssuer());
      builder.issueTime(token.getIssuedAt());
      builder.expirationTime(token.getExpirationTime());
      builder.notBeforeTime(token.getIssuedAt());
      builder.claim("username", token.getSubject());
      builder.claim("roles", convertAccessListToStringList(user.getAuthorities()));

      JWSSigner signer = new MACSigner(tokenProperties.getSecretKey());
      SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), builder.build());
      try
      {
        signedJWT.sign(signer);
        return signedJWT;
      }
      catch (JOSEException e)
      {
        L.debug(e.getMessage());
      }
    }
    catch (KeyLengthException e)
    {
      L.debug(e.getMessage());
    }
    
    return null;
  }

  private SimpleJwtToken createAndStoreToken(String username)
  {
    Date currentDate = new Date();
    Date expirationTime = new Date(currentDate.getTime() + tokenProperties.getExpirationTime());

    SimpleJwtToken token = new SimpleJwtToken();
    token.setIssuedAt(currentDate);
    token.setIssuer(tokenProperties.getIssuer());
    token.setSubject(username);
    token.setExpirationTime(expirationTime);

    return tokenRepository.save(token);
  }

  private List<String> convertAccessListToStringList(List<Access> accesses)
  {
    return accesses.stream().map(x -> new String(x.getCode())).collect(Collectors.toList());
  }
}