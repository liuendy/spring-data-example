package com.jvyang.core.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User extends AuditableEntity implements UserDetails
{

  private static final long serialVersionUID = -2244969723910768114L;

  String username;
  String password;

  FullName fullName;

  List<Profile> userProfiles;

  Boolean isAccountNotExpired;
  Boolean isAccountNotLocked;
  Boolean isCredentialsNotExpired;
  Boolean isActive;
  Integer failedAttempts;

  public User()
  {
  }

  public User(Long id)
  {
    super();
    setId(id);
  }

  public User(String username)
  {
    this.username = username == null ? null : username.trim();
  }

  public User(String username, String password)
  {
    this.username = username == null ? null : username.trim();
    this.password = password == null ? null : password.trim();
  }

  @Override
  @Column(nullable = false, unique = true, length = 32, updatable = false)
  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username == null ? null : username.trim();
  }

  @Override
  @JsonIgnore
  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password == null ? null : password.trim();
  }

  @Embedded
  public FullName getFullName()
  {
    return fullName;
  }

  public void setFullName(FullName fullName)
  {
    this.fullName = fullName;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_profiles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
      @JoinColumn(name = "profile_id") })
  public List<Profile> getUserProfiles()
  {
    return userProfiles;
  }

  public void setUserProfiles(List<Profile> userProfiles)
  {
    this.userProfiles = userProfiles;
  }

  @Transient private List<Access> authorities;

  @Override
  @JsonIgnore
  @Transient
  public List<Access> getAuthorities()
  {
    if (authorities == null || authorities.isEmpty())
    {
      authorities = new ArrayList<Access>();

      for (Profile profile : userProfiles)
      {
        List<Access> tl = profile.getAccesses();
        for (Access t : tl)
        {
          authorities.add(new Access(t.getCode()));
        }
      }

      Set<Access> hs = new HashSet<Access>();
      hs.addAll(authorities);
      authorities.clear();
      authorities.addAll(hs);

      return authorities;
    }

    return authorities;
  }

  @Column(name = "is_account_Not_expired", nullable = false)
  @Type(type = "org.hibernate.type.NumericBooleanType")
  public boolean getIsAccountNotExpired()
  {
    return isAccountNonExpired();
  }

  @Override
  @JsonIgnore
  @Transient
  public boolean isAccountNonExpired()
  {
    return isAccountNotExpired == null ? false : isAccountNotExpired;
  }

  public void setIsAccountNotExpired(Boolean isAccountNotExpired)
  {
    this.isAccountNotExpired = isAccountNotExpired;
  }

  @Column(name = "is_account_non_locked", nullable = false)
  @Type(type = "org.hibernate.type.NumericBooleanType")
  public boolean getIsAccountNotLocked()
  {
    return isAccountNonLocked();
  }

  @Override
  @JsonIgnore
  @Transient
  public boolean isAccountNonLocked()
  {
    return isAccountNotLocked == null ? false : isAccountNotLocked;
  }

  public void setIsAccountNotLocked(Boolean isAccountNotLocked)
  {
    this.isAccountNotLocked = isAccountNotLocked;
  }

  @Column(name = "is_credential_non_expired", nullable = false)
  @Type(type = "org.hibernate.type.NumericBooleanType")
  public boolean getIsCredentialsNotExpired()
  {
    return isCredentialsNonExpired();
  }

  @Override
  @JsonIgnore
  @Transient
  public boolean isCredentialsNonExpired()
  {
    return isCredentialsNotExpired == null ? false : isCredentialsNotExpired;
  }

  public void setIsCredentialsNotExpired(Boolean isCredentialsNotExpired)
  {
    this.isCredentialsNotExpired = isCredentialsNotExpired;
  }

  @Column(name = "is_active", nullable = false)
  @Type(type = "org.hibernate.type.NumericBooleanType")
  public boolean getIsActive()
  {
    return isEnabled();
  }

  @Override
  @JsonIgnore
  @Transient
  public boolean isEnabled()
  {
    return isActive == null ? false : isActive;
  }

  public void setIsActive(Boolean isActive)
  {
    this.isActive = isActive;
  }

  @Override
  public boolean equals(final Object otherObj)
  {
    if ((otherObj == null) || !(otherObj instanceof User))
    {
      return false;
    }

    final User other = (User) otherObj;
    return new EqualsBuilder().append(getId(), other.getId()).append(getId(), other.getId())
        .append(getUsername(), other.getUsername()).append(getFullName(), other.getFullName()).isEquals();
  }

  @Override
  public int hashCode()
  {
    return new HashCodeBuilder(17, 31).append(getId()).append(getId()).append(getUsername()).append(getFullName())
        .toHashCode();
  }

  @Transient
  @JsonIgnore
  public String getDelimitedProfiles()
  {
    StringJoiner sj = new StringJoiner(",");
    userProfiles.forEach(profile -> {
      sj.add(profile.getName());
    });

    return sj.toString();
  }

}
