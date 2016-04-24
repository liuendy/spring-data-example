package com.jvyang.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;

@Embeddable
public class FullName implements Serializable
{

  private static final long serialVersionUID = 1511654446929357210L;

  String lastName;
  String givenName;
  String middleName;
  String suffix;
  String title;
  String nickname;

  @Column(name = "last_name", nullable = true, length = 32)
  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName == null ? null : lastName.trim();
  }

  @Column(name = "given_name", nullable = true, length = 32)
  public String getGivenName()
  {
    return givenName;
  }

  public void setGivenName(String givenName)
  {
    this.givenName = givenName == null ? null : givenName.trim();
  }

  @Column(name = "middle_name", nullable = true, length = 32)
  public String getMiddleName()
  {
    return middleName;
  }

  public void setMiddleName(String middleName)
  {
    this.middleName = middleName == null ? null : middleName.trim();
  }

  @JsonIgnore
  @Column(name = "suffix", nullable = true, length = 8)
  public String getSuffix()
  {
    return suffix;
  }

  public void setSuffix(String suffix)
  {
    this.suffix = suffix == null ? null : suffix.trim();
  }

  @JsonIgnore
  @Column(name = "title", nullable = true, length = 12)
  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title == null ? null : title.trim();
  }

  @JsonIgnore
  @Column(name = "nickname", nullable = true, length = 16)
  public String getNickname()
  {
    return nickname;
  }

  public void setNickname(String nickname)
  {
    this.nickname = nickname == null ? null : nickname.trim();
  }

  @Transient
  public String getWholeName()
  {
    StringBuffer wholeName = new StringBuffer();

    boolean lastNameNonBlank = !Strings.isNullOrEmpty(lastName);
    if (lastNameNonBlank)
    {
      wholeName.append(lastName);
    }

    boolean givenNameNonBlank = !Strings.isNullOrEmpty(givenName);
    if (lastNameNonBlank && givenNameNonBlank)
    {
      wholeName.append(", ");
    }

    if (givenNameNonBlank)
    {
      wholeName.append(givenName);
    }

    boolean middleNameNonBlank = !Strings.isNullOrEmpty(middleName);
    if (middleNameNonBlank)
    {
      wholeName.append(" " + middleName);
    }

    boolean suffixNonBlank = !Strings.isNullOrEmpty(suffix);
    if (suffixNonBlank)
    {
      wholeName.append(" " + suffix);
    }

    return wholeName.toString();
  }

  @Override
  public String toString()
  {
    return getWholeName();
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((givenName == null) ? 0 : givenName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
    result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
    result = prime * result + ((suffix == null) ? 0 : suffix.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (!(obj instanceof FullName))
    {
      return false;
    }
    FullName other = (FullName) obj;
    if (givenName == null)
    {
      if (other.givenName != null)
      {
        return false;
      }
    }
    else if (!givenName.equals(other.givenName))
    {
      return false;
    }
    if (lastName == null)
    {
      if (other.lastName != null)
      {
        return false;
      }
    }
    else if (!lastName.equals(other.lastName))
    {
      return false;
    }
    if (middleName == null)
    {
      if (other.middleName != null)
      {
        return false;
      }
    }
    else if (!middleName.equals(other.middleName))
    {
      return false;
    }
    if (nickname == null)
    {
      if (other.nickname != null)
      {
        return false;
      }
    }
    else if (!nickname.equals(other.nickname))
    {
      return false;
    }
    if (suffix == null)
    {
      if (other.suffix != null)
      {
        return false;
      }
    }
    else if (!suffix.equals(other.suffix))
    {
      return false;
    }
    if (title == null)
    {
      if (other.title != null)
      {
        return false;
      }
    }
    else if (!title.equals(other.title))
    {
      return false;
    }
    return true;
  }

}
