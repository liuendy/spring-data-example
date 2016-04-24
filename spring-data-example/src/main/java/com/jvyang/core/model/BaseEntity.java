package com.jvyang.core.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity implements Serializable
{

  private static final long serialVersionUID = 1L;

  private Long id;
  private Long version;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  @Version
  public Long getVersion()
  {
    return version;
  }

  public void setVersion(Long version)
  {
    this.version = version;
  }

}
