package com.school.book.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.school.book.model.User;
import com.school.book.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
  private UserRepository userInfoRepository;

  @Override
  public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {

    Optional<User> user = userInfoRepository.findByUsernameAndDeletedIsFalse( username );
    if( !user.isPresent()) {
      throw new UsernameNotFoundException( "User not found with username: " + username );
    }
    return new org.springframework.security.core.userdetails.User( user.get().getUsername(), user.get().getPassword(),
        Stream.of( user.get().getRole() ).collect( Collectors.toList() ) );
  }

}
