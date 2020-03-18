package com.school.book.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.book.dto.GradesDto;
import com.school.book.model.Grades;
import com.school.book.service.GradesService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping( "/grades" )
@AllArgsConstructor
public class GradesController {
  private GradesService gradesService;

  @GetMapping( "/{userId}" )
  public List<Grades> getAllGrades( @PathVariable Long userId ) {
    return gradesService.getAllGrades( userId );
  }

  @PostMapping
  public void addGrade( @Valid @RequestBody GradesDto gradesDto ) {
    gradesService.addGrade( gradesDto );
  }

  @DeleteMapping("/{id}")
  public void deleteGrade( @PathVariable Long id ){
    gradesService.deleteGrade(id);
  }
}
