package com.school.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.school.book.model.School;
import com.school.book.model.SchoolStatistics;
import com.school.book.model.Subject;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
  @Query( "select s.name as label, avg(g.grade) as y from School s "
      + "join s.users u "
      + "join u.grades g "
      + "where g.subject = ?1 and u.deleted = false "
      + "group by s.name" )
  List<SchoolStatistics> countAllSchoolsBySubject( Subject subject );

  @Query( "select s.name as label, avg(g.grade) as y from School s "
      + "join s.users u "
      + "join u.grades g "
      + "where u.deleted = false "
      + "group by s.name" )
  List<SchoolStatistics> countAllSchools();

  @Query( "select g.subject as label, avg(g.grade) as y from School s "
      + "join s.users u "
      + "join u.grades g "
      + "where s.name = ?1 and u.deleted = false "
      + "group by g.subject" )
  List<SchoolStatistics> countSubjectBySchoolName( String name );

  @Query( "select g.subject as label, avg(g.grade) as y from School s "
      + "join s.users u "
      + "join u.grades g "
      + "where u.deleted = false "
      + "group by g.subject" )
  List<SchoolStatistics> countAllGrades();
}
