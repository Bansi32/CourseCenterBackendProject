package com.course.marketplace.dao;

import com.course.marketplace.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentDao extends JpaRepository<Student,Long> {
    @Query(value="select s from Student as s where s.fName like %:name% or s.lName like %:name%")
    Page<Student> findStudentsByName(@Param("name")String name, Pageable pageable);

    @Query(value="select s from Student as s where s.userDetails.email=:email")
    Student findStudentByEmail(@Param("email")String email);


}
