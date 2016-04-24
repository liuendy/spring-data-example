package com.jvyang.core.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatasourceProperties
{
  public static final String TO_STRING_FORMAT = "DatasourceProperties [protocol=%s, "
      + "host=%s, port=%s, dataSourceName=%s, driverClassName=%s, username=%s, password=%s]";

  @Value("${datasource.protocol}") String protocol;
  @Value("${dataSource.host}") String host;
  @Value("${dataSource.port}") String port;
  @Value("${dataSource.name}") String dataSourceName;
  @Value("${dataSource.driverClassName}") String driverClassName;
  @Value("${dataSource.username}") String username;
  @Value("${dataSource.password}") String password;

  public String getProtocol()
  {
    return protocol;
  }

  public String getHost()
  {
    return host;
  }

  public String getPort()
  {
    return port;
  }

  public String getDataSourceName()
  {
    return dataSourceName;
  }

  public String getDriverClassName()
  {
    return driverClassName;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  @Override
  public String toString()
  {
    return String.format(TO_STRING_FORMAT, protocol, host, port, dataSourceName, driverClassName, username, password);
  }
}
