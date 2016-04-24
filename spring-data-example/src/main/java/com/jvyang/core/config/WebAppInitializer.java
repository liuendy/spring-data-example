package com.jvyang.core.config;

import java.net.URI;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer
{
  private static final String CHARACTER_ENCODING_FILTER_ENCODING = "UTF-8";
  private static final String CHARACTER_ENCODING_FILTER_NAME = "characterEncoding";
  private static final String CHARACTER_ENCODING_FILTER_URL_PATTERN = "/*";

  private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
  private static final String DISPATCHER_SERVLET_MAPPING = "/";

  private static final String LOG4J2_CONFIG_PATH = "classpath:log4j2.xml";

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException
  {
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(ApplicationContext.class);

    configureLogging();

    configureDispatcherServlet(servletContext, rootContext);
    EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

    configureCharacterEncodingFilter(servletContext, dispatcherTypes);
    configureSpringSecurityFilter(servletContext, dispatcherTypes);
    servletContext.addListener(new ContextLoaderListener(rootContext));
    servletContext.addListener(new RequestContextListener());
  }

  private void configureDispatcherServlet(ServletContext servletContext, WebApplicationContext rootContext)
  {
    ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME,
        new DispatcherServlet(rootContext));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);
  }

  private void configureCharacterEncodingFilter(ServletContext servletContext, EnumSet<DispatcherType> dispatcherTypes)
  {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding(CHARACTER_ENCODING_FILTER_ENCODING);
    characterEncodingFilter.setForceEncoding(true);
    FilterRegistration.Dynamic characterEncoding = servletContext.addFilter(CHARACTER_ENCODING_FILTER_NAME,
        characterEncodingFilter);
    characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, CHARACTER_ENCODING_FILTER_URL_PATTERN);
  }

  private void configureSpringSecurityFilter(ServletContext servletContext, EnumSet<DispatcherType> dispatcherTypes)
  {
    FilterRegistration.Dynamic security = servletContext.addFilter("springSecurityFilterChain",
        new DelegatingFilterProxy());
    security.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
  }

  private void configureLogging()
  {
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    context.setConfigLocation(URI.create(LOG4J2_CONFIG_PATH));
    context.reconfigure();
  }
}