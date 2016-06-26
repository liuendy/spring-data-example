package com.jvyang.core.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@SuppressWarnings("serial")
public class JwtToken implements Authentication
{
  private static final String ROLES_CLAIM_KEY = "roles";

  final SignedJWT sjwt;
  private JWTClaimsSet claims;

  boolean authenticated;
  private final Collection<GrantedAuthority> authorities;

  public JwtToken(SignedJWT sjwt)
  {
    this.sjwt = sjwt;

    List<String> roles;
    try
    {
      roles = sjwt.getJWTClaimsSet().getStringListClaim(ROLES_CLAIM_KEY);
    }
    catch (ParseException e)
    {
      roles = new ArrayList<>();
    }
    List<GrantedAuthority> tmp = new ArrayList<>();
    if (roles != null)
    {
      for (String role : roles)
      {
        tmp.add(new SimpleGrantedAuthority(role));
      }
    }
    this.authorities = Collections.unmodifiableList(tmp);

    this.authenticated = false;
    try
    {
      claims = this.sjwt.getJWTClaimsSet();
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
  }

  public void clearClaims()
  {
    claims = new JWTClaimsSet.Builder().build();
  }

  public SignedJWT getSignedToken()
  {
    return sjwt;
  }

  public JWTClaimsSet getClaims()
  {
    return claims;
  }

  @Override
  public String getName()
  {
    return claims.getSubject();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return authorities;
  }

  @Override
  public Object getCredentials()
  {
    return "";
  }

  @Override
  public Object getDetails()
  {
    return claims.toJSONObject();
  }

  @Override
  public Object getPrincipal()
  {
    return claims.getSubject();
  }

  @Override
  public boolean isAuthenticated()
  {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException
  {
    this.authenticated = isAuthenticated;
  }

}
