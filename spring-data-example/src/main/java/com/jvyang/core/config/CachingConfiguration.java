package com.jvyang.core.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class CachingConfiguration implements CachingConfigurer
{
  @Qualifier("ehCacheCache`Manager") @Autowired(required = false) private CacheManager ehCacheCacheManager;

  @Bean(destroyMethod = "shutdown")
  public net.sf.ehcache.CacheManager ehCacheManager()
  {
    CacheConfiguration cacheConfiguration = new CacheConfiguration();
    cacheConfiguration.setName("cacheName");
    cacheConfiguration.setMaxEntriesLocalHeap(10000);
    cacheConfiguration.setMaxEntriesLocalDisk(1000);
    cacheConfiguration.setEternal(false);
    cacheConfiguration.setDiskSpoolBufferSizeMB(20);
    cacheConfiguration.setTimeToIdleSeconds(300);
    cacheConfiguration.setTimeToLiveSeconds(600);
    cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
    cacheConfiguration.setTransactionalMode("OFF");

    net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
    config.name("cacheManager");
    config.addCache(cacheConfiguration);

    return net.sf.ehcache.CacheManager.newInstance(config);
  }

  @Bean
  public CacheManager cacheManager()
  {
    List<CacheManager> cacheManagers = Lists.newArrayList();

    if (this.ehCacheCacheManager != null)
    {
      cacheManagers.add(this.ehCacheCacheManager);
    }

    cacheManagers.add(new EhCacheCacheManager(ehCacheManager()));

    CompositeCacheManager cacheManager = new CompositeCacheManager();

    cacheManager.setCacheManagers(cacheManagers);
    cacheManager.setFallbackToNoOpCache(false);

    return cacheManager;
  }

  @Bean
  @Override
  public KeyGenerator keyGenerator()
  {
    return new SimpleKeyGenerator();
  }

  @Bean
  @Override
  public CacheResolver cacheResolver()
  {
    return new SimpleCacheResolver(cacheManager());
  }

  @Bean
  @Override
  public CacheErrorHandler errorHandler()
  {
    return new SimpleCacheErrorHandler();
  }
}