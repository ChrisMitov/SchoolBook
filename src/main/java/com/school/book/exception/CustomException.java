package com.school.book.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode( callSuper = true )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {
  private String message;
  private String details;
}
