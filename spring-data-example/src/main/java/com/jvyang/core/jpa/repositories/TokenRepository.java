package com.jvyang.core.jpa.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.jvyang.core.model.SimpleJwtToken;

@Repository
@RepositoryRestResource(exported = false)
public interface TokenRepository extends BaseRepository<SimpleJwtToken, Long>
{
  public SimpleJwtToken findFirstBySubjectOrderByIssuedAtDesc(String subject);
}