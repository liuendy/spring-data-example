package com.jvyang.core.jpa.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jvyang.core.model.Profile;

@RepositoryRestResource(collectionResourceRel = "profiles", path = "profiles")
public interface ProfileRepository extends BaseRepository<Profile, Long>, QueryDslPredicateExecutor<Profile>
{
}
