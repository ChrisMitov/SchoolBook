package com.school.book.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.school.book.model.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode( callSuper = true )
@Data
public class UserDto extends BaseDto {
  @NotBlank( message = "Name is mandatory" )
  private String    name;
  @NotBlank( message = "Username is mandatory" )
  private String    username;
  @NotBlank( message = "Password is mandatory" )
  private String    password;
  private SchoolDto school;
  private String    subject;
  private String    role;
  private Set<User> children;
}
