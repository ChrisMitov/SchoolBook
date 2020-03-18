package com.school.book.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode( callSuper = false, exclude = { "parent", "children" } )
public class User extends AuditModel {
  @Id
  @GeneratedValue
  private Long        id;
  @Column( name = "name" )
  private String      name;
  @Column( name = "username" )
  private String      username;
  @Column( name = "password", nullable = false )
  private String      password;
  @Column( name = "role" )
  @Enumerated( EnumType.STRING )
  private Roles       role;
  @JsonProperty( "school" )
  @ManyToOne
  @JoinColumn( name = "school_id" )
  private School      school;
  @Enumerated( EnumType.STRING )
  private Subject     subject;
  @JsonProperty( "parent" )
  @ManyToOne()
  @JoinColumn( name = "parent_id" )
  private User        parent;
  @JsonIgnore
  @OneToMany( mappedBy = "parent" )
  private Set<User>   children = new HashSet<>();
  @JsonIgnore
  @OneToMany( mappedBy = "user")
  private Set<Grades> grades   = new HashSet<>();
  @Column( name = "classNumber" )
  private Integer     classNumber;
  @Column( name = "deleted", nullable = false)
  private boolean     deleted;
}
