package com.school.book.model;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
  Admin, Director, Teacher, Student, Parent;

  @Override public String toString() {
    return this.name();
  }

  @Override public String getAuthority() {
    return this.name();
  }
}
