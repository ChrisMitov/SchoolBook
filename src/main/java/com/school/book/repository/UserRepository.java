package com.school.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.book.model.Roles;
import com.school.book.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsernameAndDeletedIsFalse( String username );

  List<User> findAllByRoleAndDeletedIsFalse( Roles role );

  List<User> findAllByParentIdAndDeletedIsFalse( Long id );

  List<User> findAllBySchoolIdAndRoleAndDeletedIsFalse( Long id, Roles role );

}
