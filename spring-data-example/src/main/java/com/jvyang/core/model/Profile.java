package com.jvyang.core.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Embeddable
@EntityListeners(AuditingEntityListener.class)
@Table(name = "profiles")
public class Profile extends AuditableEntity
{
  private static final long serialVersionUID = 91808610523934882L;

  String name;
  String description;
  Boolean isEnabled;

  List<Access> accesses;

  public Profile()
  {
  }

  public Profile(Long id)
  {
    super();
    setId(id);
  }

  public Profile(String name)
  {
    super();
    this.name = name;
  }

  @Column(name = "profile_name", nullable = false, unique = true, length = 50)
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name == null ? null : name.trim();
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  @Type(type = "org.hibernate.type.NumericBooleanType")
  @Column(name = "is_active", nullable = false)
  public Boolean getIsActive()
  {
    return isEnabled;
  }

  public void setIsActive(Boolean isEnabled)
  {
    this.isEnabled = isEnabled;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "profile_accesses", joinColumns = { @JoinColumn(name = "profile_id") }, inverseJoinColumns = {
      @JoinColumn(name = "access_id") })
  public List<Access> getAccesses()
  {
    return accesses;
  }

  public void setAccesses(List<Access> accesses)
  {
    this.accesses = accesses;
  }

  @Override
  public boolean equals(final Object otherObj)
  {
    if ((otherObj == null) || !(otherObj instanceof Profile))
    {
      return false;
    }

    final Profile other = (Profile) otherObj;
    return new EqualsBuilder().append(getId(), other.getId()).append(getName(), other.getName()).isEquals();
  }

  @Override
  public int hashCode()
  {
    return new HashCodeBuilder(17, 31).append(getId()).append(getId()).append(getName()).toHashCode();
  }

}