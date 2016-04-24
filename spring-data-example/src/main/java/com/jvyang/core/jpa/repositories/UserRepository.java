package com.jvyang.core.jpa.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jvyang.core.model.User;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends BaseRepository<User, Long>, QueryDslPredicateExecutor<User>
{
}
