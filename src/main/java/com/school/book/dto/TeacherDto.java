package com.school.book.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode( callSuper = true )
@Data
public class TeacherDto extends BaseDto {
  @NotBlank( message = "Name is mandatory" )
  private String    name;
  @NotBlank( message = "Username is mandatory" )
  private String    username;
  @NotBlank( message = "Password is mandatory" )
  private String    password;
  private SchoolDto school;
  private String    subject;
}
