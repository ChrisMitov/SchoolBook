package com.school.book.service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.book.dto.StudentDto;
import com.school.book.dto.TeacherDto;
import com.school.book.dto.UserDto;
import com.school.book.exception.CustomException;
import com.school.book.model.Roles;
import com.school.book.model.School;
import com.school.book.model.User;
import com.school.book.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
  private UserRepository userRepository;
  @Qualifier( "customJson" )
  private ObjectMapper   customJson;

  public User createUser( User user ) {
    final Optional<User> userByUsername = userRepository.findByUsernameAndDeletedIsFalse( user.getUsername() );
    if( userByUsername.isPresent() ) {
      throw new CustomException( "Username already exists!", "User with this username already exists!" );
    }
    String encodedPassword = new BCryptPasswordEncoder().encode( user.getPassword() );
    user.setPassword( encodedPassword );
    return userRepository.saveAndFlush( user );
  }

  public User updateUser( User user ) {
    getUserByName( user.getUsername() );
    return userRepository.saveAndFlush( user );
  }

  public User getUserById( Long id ) {
    return userRepository.findById( id )
        .filter( user -> !user.isDeleted() )
        .orElseThrow( () -> new CustomException( "User not found!", "User with this id not found!" ) );
  }

  public void deleteUser( Long id ) {
    final User userById = getUserById( id );
    userById.setDeleted( true );
    userRepository.saveAndFlush( userById );
  }

  public List<Roles> getRoles() {
    return EnumSet.allOf( Roles.class ).stream()
        .filter( roles -> !roles.equals( Roles.Admin ) )
        .collect( Collectors.toList() );
  }

  public TeacherDto createTeacher( TeacherDto teacher ) {
    final User user = customJson.convertValue( teacher, User.class );
    user.setRole( Roles.Teacher );
    return customJson.convertValue( createUser( user ), TeacherDto.class );
  }

  public void addStudentToClass( StudentDto studentDto ) {
    //    final User user = getUserById( studentDto.getSchoolId() );
    //    final School school = schoolService.getSchool( studentDto.getSchoolId() );
    //    user.setSchool( school );
    //    user.setClassNumber( studentDto.getClassNumber() );
    //    userRepository.save( user );
  }

  public void createStudent( StudentDto studentDto ) {
    User user = setStudent( studentDto );
    createUser( user );
  }

  public void updateStudent( StudentDto studentDto ) {
    User user = setStudent( studentDto );
    updateUser( user );
  }

  private User setStudent( StudentDto studentDto ) {
    final User user = customJson.convertValue( studentDto, User.class );
    final User parent = customJson.convertValue( studentDto.getParent(), User.class );
    final School school = customJson.convertValue( studentDto.getSchool(), School.class );
    user.setClassNumber( studentDto.getClassNumber() );
    user.setSchool( school );
    user.setParent( parent );
    user.setRole( Roles.Student );
    return user;
  }

  public List<StudentDto> getStudentOfParent( Long id ) {
    return userRepository.findAllByParentIdAndDeletedIsFalse( id ).stream()
        .map( students -> customJson.convertValue( students, StudentDto.class ) )
        .collect( Collectors.toList() );
  }

  public List<UserDto> getParents() {
    final List<User> allParents = userRepository.findAllByRoleAndDeletedIsFalse( Roles.Parent );
    return allParents.stream()
        .map( parent -> customJson.convertValue( parent, UserDto.class ) )
        .collect( Collectors.toList() );
  }

  public List<StudentDto> getStudents() {
    final List<User> allStudents = userRepository.findAllByRoleAndDeletedIsFalse( Roles.Student );
    return allStudents.stream()
        .map( students -> customJson.convertValue( students, StudentDto.class ) )
        .collect( Collectors.toList() );
  }

  public List<UserDto> getTeachers() {
    final List<User> teachers = userRepository.findAllByRoleAndDeletedIsFalse( Roles.Teacher );
    return teachers.stream()
        .map( students -> customJson.convertValue( students, UserDto.class ) )
        .collect( Collectors.toList() );
  }

  public List<StudentDto> getStudents( String name ) {
    final User teacher = getUserByName( name );
    final List<User> students = userRepository.findAllBySchoolIdAndRoleAndDeletedIsFalse( teacher.getSchool().getId(), Roles.Student );
    return students.stream()
        .map( student -> customJson.convertValue( student, StudentDto.class ) )
        .collect( Collectors.toList() );
  }

  public User getUserByName( String name ) {
    final Optional<User> teacher = userRepository.findByUsernameAndDeletedIsFalse( name );
    if( !teacher.isPresent() ) {
      throw new CustomException( "Username not exists!", "User with this username not exists!" );
    }
    return teacher.get();
  }
}
