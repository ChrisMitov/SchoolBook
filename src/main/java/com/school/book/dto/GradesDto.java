package com.school.book.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode( callSuper = true )
@Data
public class GradesDto extends BaseDto {
  @NotBlank( message = "Subject is mandatory" )
  private String  subject;
  @NotNull( message = "Grade is mandatory" )
  private Integer grade;
  @NotNull( message = "User is mandatory" )
  private UserDto user;
}
