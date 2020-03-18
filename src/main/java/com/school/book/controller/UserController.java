package com.school.book.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.book.dto.StudentDto;
import com.school.book.dto.TeacherDto;
import com.school.book.dto.UserDto;
import com.school.book.model.Roles;
import com.school.book.model.User;
import com.school.book.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping( "/user" )
@AllArgsConstructor
public class UserController {

  private UserService  userService;
  @Qualifier( "customJson" )
  private ObjectMapper customJson;

  @GetMapping( "/{id}" )
  public UserDto getUserById( @PathVariable String id ) {
    final User user = userService.getUserById( Long.parseLong( id ) );
    return customJson.convertValue( user, UserDto.class );
  }

  @GetMapping( "/name/{name}" )
  public UserDto getUserByUsername( @PathVariable String name ) {
    final User user = userService.getUserByName( name );
    return customJson.convertValue( user, UserDto.class );
  }

  @DeleteMapping( "/{id}" )
  public void deleteUser( @PathVariable String id ) {
    userService.deleteUser( Long.parseLong( id ) );
  }

  @GetMapping( "/student" )
  public List<StudentDto> getStudents() {
    return userService.getStudents();
  }

  @GetMapping( "/getStudentsForTeacher" )
  public List<StudentDto> getStudentsForTeacher() {
    return userService.getStudents( SecurityContextHolder.getContext().getAuthentication().getName() );
  }

  @GetMapping( "/parent" )
  public List<UserDto> getParents() {
    return userService.getParents();
  }

  @GetMapping( "/teacher" )
  public List<UserDto> getTeachers() {
    return userService.getTeachers();
  }

  @GetMapping( "/parent/students/{id}" )
  public List<StudentDto> getStudentsOfParent( @PathVariable String id ) {
    return userService.getStudentOfParent( Long.parseLong( id ) );
  }

  @PostMapping
  public UserDto createUser( @Valid @RequestBody UserDto userDto ) {
    final User user = customJson.convertValue( userDto, User.class );
    return customJson.convertValue( userService.createUser( user ), UserDto.class );
  }

  @GetMapping( "/roles" )
  public List<Roles> getRoles() {
    return userService.getRoles();
  }

  @PostMapping( "/director" )
  public UserDto createDirector( @Valid @RequestBody UserDto userDto ) {
    final User user = customJson.convertValue( userDto, User.class );
    user.setRole( Roles.Director );
    return customJson.convertValue( userService.createUser( user ), UserDto.class );
  }

  @PostMapping( "/parent" )
  public UserDto createParent( @Valid @RequestBody UserDto userDto ) {
    final User user = customJson.convertValue( userDto, User.class );
    user.setRole( Roles.Parent );
    return customJson.convertValue( userService.createUser( user ), UserDto.class );
  }

  @PutMapping( "/parent" )
  public UserDto updateParent( @Valid @RequestBody UserDto userDto ) {
    final User user = customJson.convertValue( userDto, User.class );
    user.setRole( Roles.Parent );
    return customJson.convertValue( userService.updateUser( user ), UserDto.class );
  }

  @PostMapping( "/teacher" )
  public TeacherDto createTeacher( @Valid @RequestBody TeacherDto teacherDto ) {
    return userService.createTeacher( teacherDto );
  }

  @PutMapping( "/teacher" )
  public UserDto updateTeacher( @Valid @RequestBody UserDto userDto ) {
    final User user = customJson.convertValue( userDto, User.class );
    user.setRole( Roles.Teacher );
    return customJson.convertValue( userService.updateUser( user ), UserDto.class );
  }

  @PostMapping( "/student" )
  public void createStudent( @Valid @RequestBody StudentDto studentDto ) {
    userService.createStudent( studentDto );
  }

  @PostMapping( "/addStudentToClass" )
  public void addStudentToClass( @RequestBody StudentDto studentDto ) {
    userService.addStudentToClass( studentDto );
  }

  @PutMapping( "/student" )
  public void updateStudent( @Valid @RequestBody StudentDto studentDto ) {
    userService.updateStudent( studentDto );
  }

}
