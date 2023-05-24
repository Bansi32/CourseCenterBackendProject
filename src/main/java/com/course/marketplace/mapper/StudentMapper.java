package com.course.marketplace.mapper;

import com.course.marketplace.dao.StudentDao;
import com.course.marketplace.dto.StudentDto;
import com.course.marketplace.entity.Student;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {
    public StudentDto fromStudent(Student student)
    {
        StudentDto studentDto=new StudentDto();
        BeanUtils.copyProperties(student,studentDto);
        return studentDto;
    }
    public Student fromStudentDto(StudentDto studentDto)
    {
        Student student=new Student();
        BeanUtils.copyProperties(studentDto,student);
        return student;
    }
}
