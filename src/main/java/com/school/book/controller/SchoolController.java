package com.school.book.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.book.dto.SchoolDto;
import com.school.book.model.School;
import com.school.book.model.SchoolStatistics;
import com.school.book.model.Subject;
import com.school.book.service.SchoolService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping( "/school" )
@AllArgsConstructor
public class SchoolController {
  private SchoolService schoolService;

  @GetMapping
  public List<School> getSchools() {
    return schoolService.getSchools();
  }

  @PostMapping
  public School createSchool( @Valid @RequestBody SchoolDto school ) {
    return schoolService.addSchool( school );
  }

  @GetMapping( "/subject" )
  public Set<Subject> getAllSubjects() {
    return schoolService.getAllSubjects();
  }

  @PutMapping
  public School updateSchool( @RequestBody SchoolDto school ) {
    return schoolService.updateSchool( school );
  }

  @DeleteMapping( "/{id}" )
  public void deleteSchool( @PathVariable String id ) {
    schoolService.deleteSchool( id );
  }

  @GetMapping( "/statistics/{type}" )
  public List<SchoolStatistics> getStatistics( @PathVariable String type ) {
    return schoolService.getStatistics( type );
  }

  @GetMapping( "/statistics/school/{name}" )
  public List<SchoolStatistics> getStatisticsBySchoolName( @PathVariable String name ) {
    return schoolService.getStatisticsBySchoolName( name );
  }
}
