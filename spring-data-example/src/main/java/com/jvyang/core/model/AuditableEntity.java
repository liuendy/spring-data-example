package com.jvyang.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public abstract class AuditableEntity extends BaseEntity
{
  private static final long serialVersionUID = 1L;

  private String createdBy;
  private Date createdDate;
  private String lastModifiedBy;
  private Date lastModifiedDate;

  @CreatedBy
  @Column(name = "created_by", nullable = false, length = 32, updatable = false)
  public String getCreatedBy()
  {
    return createdBy;
  }

  public void setCreatedBy(String createdBy)
  {
    this.createdBy = createdBy;
  }

  @CreatedDate
  @Column(name = "created_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  public Date getCreatedDate()
  {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate)
  {
    this.createdDate = createdDate;
  }

  @LastModifiedBy
  @Column(name = "last_modified_by", nullable = false, length = 32, updatable = false)
  public String getLastModifiedBy()
  {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy)
  {
    this.lastModifiedBy = lastModifiedBy;
  }

  @LastModifiedDate
  @Column(name = "last_modified_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  public Date getLastModifiedDate()
  {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate)
  {
    this.lastModifiedDate = lastModifiedDate;
  }

}
