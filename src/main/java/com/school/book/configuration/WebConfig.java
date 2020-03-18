package com.school.book.configuration;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import org.h2.server.web.WebServlet;

@Configuration
public class WebConfig {
  @Bean( name = "customJson" )
  @Primary
  public ObjectMapper customJson() {
    return new Jackson2ObjectMapperBuilder()
        .indentOutput( true )
        .serializationInclusion( JsonInclude.Include.NON_NULL )
        .propertyNamingStrategy( PropertyNamingStrategy.LOWER_CAMEL_CASE )
        .build();
  }

  @Bean
  ServletRegistrationBean h2servletRegistration() {
    ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet() );
    registrationBean.addUrlMappings( "/console/*" );
    return registrationBean;
  }
}
