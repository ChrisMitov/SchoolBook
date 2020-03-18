package com.school.book.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Data
@EqualsAndHashCode( callSuper = true )
public class Grades extends AuditModel {
  @Id
  @GeneratedValue
  private Long    id;
  @Enumerated( EnumType.STRING )
  private Subject subject;
  @Column( name = "grade" )
  private Integer grade;
  @ManyToOne
  @JoinColumn( name = "user_id" )
  private User    user;
}
