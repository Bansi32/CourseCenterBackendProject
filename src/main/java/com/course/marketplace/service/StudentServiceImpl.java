package com.course.marketplace.service;

import com.course.marketplace.dao.StudentDao;
import com.course.marketplace.dto.StudentDto;
import com.course.marketplace.entity.Course;
import com.course.marketplace.entity.Student;
import com.course.marketplace.entity.UserDetails;
import com.course.marketplace.mapper.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class StudentServiceImpl implements  StudentService{

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    public Student getStudentById(Long studentId) {

        return studentDao.findById(studentId).orElseThrow(()->new EntityNotFoundException("Student id "+studentId+" not found!"));
    }

    @Override
    public Page<StudentDto> getStudentsByName(String name, int page, int size) {
        PageRequest pageRequest=PageRequest.of(page,size);
        Page<Student> studentPage=studentDao.findStudentsByName(name,pageRequest);

        return new PageImpl<>(studentPage.getContent().stream().map(student -> studentMapper.fromStudent(student)).collect(Collectors.toList()),pageRequest, studentPage.getTotalElements());
    }

    @Override
    public StudentDto getStudentByEmail(String email) {
        return studentMapper.fromStudent(studentDao.findStudentByEmail(email));
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        UserDetails userDetails=userDetailsService.createUser(studentDto.getUserDetailsDto().getUserName(),studentDto.getUserDetailsDto().getEmail(),studentDto.getUserDetailsDto().getPassword());
        userDetailsService.assignRoleToUser(userDetails.getEmail(),"Student");
        Student student=studentMapper.fromStudentDto(studentDto);
        student.setUserDetails(userDetails);
        Student savedStudent=studentDao.save(student);
        return studentMapper.fromStudent(savedStudent);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        Student getStudent=getStudentById(studentDto.getStudentId());
        Student student=studentMapper.fromStudentDto(studentDto);
        student.setUserDetails(getStudent.getUserDetails());
        student.setCourses(getStudent.getCourses());
        Student updatedStudent=studentDao.save(student);
        return studentMapper.fromStudent(updatedStudent);
    }

    @Override
    public void removeStudent(Long studentId) {
        Student student=getStudentById(studentId);
        Iterator<Course> courseIterator=student.getCourses().iterator();
        if(courseIterator.hasNext())
        {
            Course course=courseIterator.next();
            course.removeStudentFromCourse(student);
        }
        studentDao.deleteById(studentId);

    }
}
