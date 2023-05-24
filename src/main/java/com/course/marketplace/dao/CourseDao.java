package com.course.marketplace.dao;

import com.course.marketplace.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseDao extends JpaRepository<Course,Long> {

   // @Query(value="select * from courses as c where c.course_name like %:keyword%",nativeQuery = true)
    Page<Course> findCoursesByCourseNameContains(String keyword, Pageable pageable);

    @Query(value="select * from courses as c where c.course_id in (select e.course_id from enrolled_in as e where e.student_id=:studentId)",nativeQuery = true)
    Page<Course> getCoursesByStudentId(@Param("studentId")Long studentId,Pageable pageable);

    @Query(value="select * from courses as c where c.course_id not in (select e.course_id from enrolled_in as e where e.student_id=:studentId)",nativeQuery = true)
    Page<Course> getNonEnrolledInCoursesByStudentId(@Param("studentId")Long studentId,Pageable pageable);

    @Query(value = "select c from Course as c where c.tutor.tutorId=:tutorId")
    Page<Course> getCoursesByTutorId(@Param("tutorId") Long tutorId,Pageable pageable);

}
