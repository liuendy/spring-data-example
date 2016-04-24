package com.jvyang.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "tokens")
public class SimpleJwtToken extends BaseEntity implements Serializable
{

  private static final long serialVersionUID = 4195533036540998608L;

  private String issuer;
  private String subject;
  private Date expirationTime;
  private Date issuedAt;

  @Column(nullable = false, length = 32)
  public String getIssuer()
  {
    return issuer;
  }

  public void setIssuer(String issuer)
  {
    this.issuer = issuer;
  }

  @Column(nullable = false, length = 32)
  public String getSubject()
  {
    return subject;
  }

  public void setSubject(String subject)
  {
    this.subject = subject;
  }

  @Column(name = "expiration_time", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  public Date getExpirationTime()
  {
    return expirationTime;
  }

  public void setExpirationTime(Date expirationTime)
  {
    this.expirationTime = expirationTime;
  }

  @Column(name = "issued_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  public Date getIssuedAt()
  {
    return issuedAt;
  }

  public void setIssuedAt(Date issuedAt)
  {
    this.issuedAt = issuedAt;
  }

  @Override
  public boolean equals(final Object otherObj)
  {
    if ((otherObj == null) || !(otherObj instanceof SimpleJwtToken))
    {
      return false;
    }

    final SimpleJwtToken other = (SimpleJwtToken) otherObj;
    return new EqualsBuilder().append(getId(), other.getId()).append(getIssuer(), other.getIssuer())
        .append(getSubject(), other.getSubject()).isEquals();
  }

  @Override
  public int hashCode()
  {
    return new HashCodeBuilder(17, 31).append(getId()).append(getIssuer()).append(getSubject()).toHashCode();
  }
}