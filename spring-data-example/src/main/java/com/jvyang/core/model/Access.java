package com.jvyang.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "accesses")
public class Access extends BaseEntity implements GrantedAuthority
{

  private static final long serialVersionUID = -1363287958292370263L;

  private String code;
  private String name;
  private String description;

  public Access()
  {
  }

  public Access(String role)
  {
    Assert.hasText(role, "A granted authority textual representation is required");
    code = role;
  }

  @Column(name = "access_code", nullable = false, unique = true, length = 50)
  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code == null ? null : code.trim();
  }

  @Column(name = "access_name", nullable = false, unique = true, length = 50)
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  @Override
  @Transient
  @JsonIgnore
  public String getAuthority()
  {
    return this.getCode();
  }

  @Override
  public boolean equals(final Object otherObj)
  {
    if ((otherObj == null) || !(otherObj instanceof Access))
    {
      return false;
    }

    final Access other = (Access) otherObj;
    return new EqualsBuilder().append(getCode(), other.getCode()).isEquals();
  }

  @Override
  public int hashCode()
  {

    int hashCode = 17;

    hashCode += null == getCode() ? 0 : getCode().hashCode() * 31;

    return hashCode;
  }

  @Override
  public String toString()
  {
    return getCode();
  }
}
