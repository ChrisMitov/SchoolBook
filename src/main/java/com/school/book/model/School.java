package com.school.book.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode( callSuper = false  )
public class School extends AuditModel {
  @Id
  @GeneratedValue
  private Long id;
  @Column( name = "name" )
  private String name;
  @Column(name = "address")
  private String    address;
  @JsonIgnore
  @OneToMany( mappedBy = "school" )
  private Set<User> users = new HashSet<>();

}
