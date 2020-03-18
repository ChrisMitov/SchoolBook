package com.school.book.service;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.book.dto.SchoolDto;
import com.school.book.exception.CustomException;
import com.school.book.model.School;
import com.school.book.model.SchoolStatistics;
import com.school.book.model.Subject;
import com.school.book.repository.SchoolRepository;
import com.sun.deploy.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SchoolService {
  private SchoolRepository schoolRepository;
  @Qualifier( "customJson" )
  private ObjectMapper     customJson;

  public School addSchool( School school ) {
    return schoolRepository.save( school );
  }

  public List<School> getSchools() {
    return schoolRepository.findAll();
  }

  public School getSchool( Long id ) {
    return schoolRepository.findById( id )
        .orElseThrow( () -> new CustomException( "School not found!", "School with this id not found!" ) );
  }

  public Set<Subject> getAllSubjects() {
    return EnumSet.allOf( Subject.class );
  }

  public School updateSchool( SchoolDto schoolDto ) {
    final School school = customJson.convertValue( schoolDto, School.class );
    return schoolRepository.save( school );
  }

  public void deleteSchool( String id ) {
    final School school = getSchool( Long.parseLong( id ) );
    schoolRepository.delete( school );
  }

  public List<SchoolStatistics> getStatistics( String subject ) {
    if( subject == null || subject.equals( "null" ) ) {
      return schoolRepository.countAllGrades();
    }
    return schoolRepository.countAllSchoolsBySubject( Subject.valueOf( subject ) );
  }

  public List<SchoolStatistics> getStatisticsBySchoolName( String name ) {
    if( name == null || name.equals( "null" ) ) {
      return schoolRepository.countAllSchools();
    }
    return schoolRepository.countSubjectBySchoolName( name );
  }
}
