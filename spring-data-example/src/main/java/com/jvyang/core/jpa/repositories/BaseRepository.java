package com.jvyang.core.jpa.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>
{
  // void delete(T deleted);

  // List<T> findAll();

  // Optional<T> findOne(ID id);

  // T save(T persisted);
}