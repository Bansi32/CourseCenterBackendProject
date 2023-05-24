package com.course.marketplace.dao;

import com.course.marketplace.entity.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TutorDao extends JpaRepository<Tutor,Long> {

@Query(value="select t from Tutor as t where t.fName like %:name% or t.lName like %:name%")
Page<Tutor> findTutorsByName(@Param("name")String name, Pageable pageable);

@Query(value="select t from Tutor as t where t.userDetails.email=:email")
    Tutor findTutorByEmail(@Param("email") String email);

@Query(value="select t from Tutor as t where t.isActive in (false)")
List<Tutor> getAllInActiveTutor();

@Query(value="select t from Tutor as t where t.isActive in (true)")
List<Tutor> getAllActiveTutor();

}
