package com.school.book.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class StudentDto {
  private Long id;
  @NotBlank( message = "Name is mandatory" )
  private String  name;
  @NotBlank( message = "Username is mandatory" )
  private String  username;
  @NotBlank( message = "Password is mandatory" )
  private String  password;
  private SchoolDto school;
  private UserDto parent;
  private Long    parentId;
  private Integer classNumber;
}
