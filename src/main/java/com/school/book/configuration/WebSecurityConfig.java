package com.school.book.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.school.book.service.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @Autowired
  private JwtUserDetailsService       jwtUserDetailsService;
  @Autowired
  private JwtRequestFilter            jwtRequestFilter;

  @Autowired
  public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {

    auth.userDetailsService( jwtUserDetailsService ).passwordEncoder( passwordEncoder() );

  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings( CorsRegistry registry ) {
        registry.addMapping( "/**" )
            .allowedOrigins( "http://localhost:4200" )
            .allowedMethods( "GET", "POST", "OPTIONS", "PUT", "DELETE" );
      }
    };
  }

  @Bean
  public JwtAuthenticationEntryPoint jwtAuthenticationEntryPointBean() throws Exception {
    return new JwtAuthenticationEntryPoint();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();

  }

  @Override
  protected void configure( HttpSecurity httpSecurity ) throws Exception {

    httpSecurity.csrf().disable()
        .authorizeRequests().antMatchers( "/console/**" ).permitAll().and()

        .authorizeRequests().antMatchers( "/authenticate", "/user", "/user/roles" ).permitAll()

        .anyRequest().authenticated().and().

        exceptionHandling().authenticationEntryPoint( jwtAuthenticationEntryPoint ).and().sessionManagement()

        .sessionCreationPolicy( SessionCreationPolicy.STATELESS );
    httpSecurity.headers().frameOptions().disable();
    httpSecurity.cors();
    httpSecurity.addFilterBefore( jwtRequestFilter, UsernamePasswordAuthenticationFilter.class );

  }

}
