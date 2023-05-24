package com.course.marketplace.service;

import com.course.marketplace.dto.StudentDto;
import com.course.marketplace.entity.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
    Student getStudentById(Long studentId);
    Page<StudentDto> getStudentsByName(String name,int page,int size);
    StudentDto getStudentByEmail(String email);
    StudentDto createStudent (StudentDto studentDto);
    StudentDto updateStudent(StudentDto studentDto);
    void removeStudent(Long studentId);

}
