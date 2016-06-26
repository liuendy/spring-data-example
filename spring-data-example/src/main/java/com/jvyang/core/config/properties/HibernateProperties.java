package com.jvyang.core.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HibernateProperties
{
  public static final String TO_STRING_FORMAT = "HibernateProperties [dialect=%s, "
      + "hbm2ddlAuto=%s, ejbNamingStrategy=%s, showSql=%s, formatSql=%s, cacheRegionPrefix=%s, importFiles=%s]";

  @Value("${hibernate.dialect}") private String dialect;
  @Value("${hibernate.hbm2ddl.auto}") private String hbm2ddlAuto;
  @Value("${hibernate.physical_naming_strategy}") private String physicalNamingStrategy;
  @Value("${hibernate.show_sql}") private String showSql;
  @Value("${hibernate.format_sql}") private String formatSql;
  @Value("${hibernate.cache.region.factory_class}") private String cacheRegionFactoryClass;
  @Value("${hibernate.temp.use_jdbc_metadata_defaults}") private String useJdbcMetadataDefaults;
  @Value("${hibernate.cache.region_prefix}") private String cacheRegionPrefix;
  @Value("${hibernate.hbm2ddl.import_files}") private String importFiles;

  public String getDialect()
  {
    return dialect;
  }

  public String getHbm2ddlAuto()
  {
    return hbm2ddlAuto;
  }

  public String getPhysicalNamingStrategy()
  {
    return physicalNamingStrategy;
  }

  public String getShowSql()
  {
    return showSql;
  }

  public String getFormatSql()
  {
    return formatSql;
  }

  public String getCacheRegionFactoryClass()
  {
    return cacheRegionFactoryClass;
  }

  public String getUseJdbcMetadataDefaults()
  {
    return useJdbcMetadataDefaults;
  }

  public String getCacheRegionPrefix()
  {
    return cacheRegionPrefix;
  }

  public String getImportFiles()
  {
    return importFiles;
  }

  @Override
  public String toString()
  {
    return String.format(TO_STRING_FORMAT, dialect, hbm2ddlAuto, physicalNamingStrategy, showSql, formatSql,
        cacheRegionPrefix, importFiles);
  }
}
