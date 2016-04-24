package com.jvyang.core.logging;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingAdvice implements MethodInterceptor
{

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable
  {
    long startTime;
    long elapsedTime;
    Object returnValue;

    Method method = invocation.getMethod();
    Object[] parameters = invocation.getArguments();

    Logger log = LogManager.getLogger("AOPCoreLogger");
    log.info("Invoking: " + method.getDeclaringClass().getName() + "." + method.getName() + "()");

    if (log.isDebugEnabled() && parameters.length > 0)
    {
      StringBuilder message = new StringBuilder("Method paramters: ");

      for (int i = 0; i < parameters.length; i++)
      {

        message.append(ToStringBuilder.reflectionToString(parameters[i]));

        if ((parameters.length - 1) != i)
        {
          message.append(",");
        }

      }

      log.debug(message.toString());
    }

    startTime = System.currentTimeMillis();
    try
    {
      returnValue = invocation.proceed();
      elapsedTime = System.currentTimeMillis() - startTime;

      log.info("Invoking complete: " + method.getDeclaringClass().getName() + "." + method.getName() + "() in "
          + elapsedTime + "ms");
    }
    catch (Exception e)
    {
      elapsedTime = System.currentTimeMillis() - startTime;

      log.error("Exception occured for " + method.getName() + "()", e);
      log.warn("Invoking complete(WITH EXCEPTION): " + method.getDeclaringClass().getName() + "." + method.getName()
          + "() in " + elapsedTime + "ms");

      throw e;
    }

    return returnValue;

  }

}
