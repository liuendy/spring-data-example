package com.jvyang.core.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jvyang.core.config.properties.DatasourceProperties;
import com.jvyang.core.config.properties.HibernateProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(basePackages = { "com.jvyang.core.jpa" })
@EnableTransactionManagement
class PersistenceContext
{
  @Autowired private DatasourceProperties datasourceProperties;
  @Autowired private HibernateProperties hibernateProperties;

  @Bean(destroyMethod = "close")
  public DataSource dataSource()
  {
    final String url = String.format("jdbc:%s%s:%s%s", datasourceProperties.getProtocol(),
        datasourceProperties.getHost(), datasourceProperties.getPort(), datasourceProperties.getDataSourceName());

    HikariConfig dataSourceConfig = new HikariConfig();
    dataSourceConfig.setDriverClassName(datasourceProperties.getDriverClassName());
    dataSourceConfig.setJdbcUrl(url);
    dataSourceConfig.setUsername(datasourceProperties.getUsername());
    dataSourceConfig.setPassword(datasourceProperties.getPassword());

    return new HikariDataSource(dataSourceConfig);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource)
  {
    Properties prop = new Properties();
    prop.put("hibernate.dialect", hibernateProperties.getDialect());
    prop.put("hibernate.hbm2ddl.auto", hibernateProperties.getHbm2ddlAuto());
    prop.put("hibernate.physical_naming_strategy", hibernateProperties.getPhysicalNamingStrategy());
    prop.put("hibernate.show_sql", hibernateProperties.getShowSql());
    prop.put("hibernate.format_sql", hibernateProperties.getFormatSql());
    prop.put("hibernate.cache.region.factory_class", hibernateProperties.getCacheRegionFactoryClass());
    prop.put("hibernate.temp.use_jdbc_metadata_defaults", hibernateProperties.getUseJdbcMetadataDefaults());
    prop.put("hibernate.cache.region_prefix", hibernateProperties.getCacheRegionFactoryClass());

    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("com.jvyang.core.model");
    entityManagerFactoryBean.setJpaProperties(prop);

    return entityManagerFactoryBean;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory)
  {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);

    return transactionManager;
  }

}