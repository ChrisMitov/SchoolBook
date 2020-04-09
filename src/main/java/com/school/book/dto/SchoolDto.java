package com.school.book.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode( callSuper = true )
@Data
public class SchoolDto extends BaseDto {
  private String id;
  @NotBlank( message = "Name is mandatory" )
  private String name;
  @NotBlank( message = "Address is mandatory" )
  private String address;
}
