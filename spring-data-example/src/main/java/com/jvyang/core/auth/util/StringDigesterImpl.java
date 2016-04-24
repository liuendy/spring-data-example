package com.jvyang.core.auth.util;

import org.jasypt.digest.StandardStringDigester;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public final class StringDigesterImpl
{

  @Value("${security.digester.algorithm}") private String algorithm;
  @Value("${security.digester.iterations}") private Integer iterations;
  @Value("${security.digester.saltSizeBytes}") private Integer saltSizeBytes;

  @Bean
  public StandardStringDigester stringDigester()
  {
    StandardStringDigester digester = new StandardStringDigester();

    // Configure digester
    digester.setAlgorithm(algorithm);
    digester.setIterations(iterations);

    // Generates salt
    RandomSaltGenerator saltGenerator = new RandomSaltGenerator();
    saltGenerator.generateSalt(saltSizeBytes);

    digester.setSaltGenerator(saltGenerator);

    if (!digester.isInitialized())
    {
      digester.initialize();
    }

    return digester;
  }

}