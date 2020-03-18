package com.school.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.book.dto.GradesDto;
import com.school.book.model.Grades;
import com.school.book.repository.GradesRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GradesService {
  private GradesRepository gradesRepository;

  @Qualifier( "customJson" )
  private ObjectMapper customJson;

  public List<Grades> getAllGrades( Long userId ) {
    return gradesRepository.getAllByUserId( userId );
  }

  public void addGrade( GradesDto gradesDto ) {
    final Grades grades = customJson.convertValue( gradesDto, Grades.class );
    gradesRepository.save( grades );
  }

  public void deleteGrade( Long id ) {
    gradesRepository.deleteById( id );
  }
}
