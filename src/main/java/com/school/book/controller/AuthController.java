package com.school.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.school.book.configuration.JwtToken;
import com.school.book.exception.CustomException;
import com.school.book.model.JwtRequest;
import com.school.book.model.JwtResponse;
import com.school.book.service.JwtUserDetailsService;

@RestController
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtToken jwtToken;

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;

  @RequestMapping( value = "/authenticate", method = RequestMethod.POST )
  public ResponseEntity<?> createAuthenticationToken( @RequestBody JwtRequest authenticationRequest ) throws Exception {

    authenticate( authenticationRequest.getUsername(), authenticationRequest.getPassword() );

    final UserDetails userDetails = jwtUserDetailsService

        .loadUserByUsername( authenticationRequest.getUsername() );

    final String token = jwtToken.generateToken( userDetails );

    return ResponseEntity.ok( new JwtResponse( token ) );

  }

  private void authenticate( String username, String password ) throws Exception {

    try {

      authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( username, password ) );

    } catch( DisabledException e ) {

      throw new CustomException( "USER_DISABLED", e.getMessage() );

    } catch( BadCredentialsException e ) {

      throw new CustomException( "Invalid credentials", e.getMessage() );

    }

  }

}
