package com.school.book.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.school.book.model.Grades;

@Repository
public interface GradesRepository extends CrudRepository<Grades, Long> {
  List<Grades> getAllByUserId( Long userId );
}
