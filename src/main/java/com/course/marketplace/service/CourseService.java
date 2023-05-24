package com.course.marketplace.service;

import com.course.marketplace.dto.CourseDto;
import com.course.marketplace.entity.Course;
import org.springframework.data.domain.Page;

public interface CourseService {
    Course getCourseById(Long courseId);
    CourseDto createCourse(CourseDto courseDto);
    CourseDto updateCourse(CourseDto courseDto);
    Page<CourseDto> findCoursesByCourseName(String keyword,int page,int size);
    void assignStudentToCourse(Long courseId,Long studentId);
    Page<CourseDto> fetchCoursesForStudent(Long studentId,int page,int size);
    Page<CourseDto> fetchNonEnrolledInCoursesForStudent(Long studentId,int page,int size);
    void removeCourse(Long courseId);
    Page<CourseDto> fetchCoursesForTutor(Long tutorId,int page,int size);


}
