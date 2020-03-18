package com.school.book.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode( callSuper = true )
@Data
public class SchoolDto extends BaseDto {
  private String id;
  private String name;
  private String address;
}
