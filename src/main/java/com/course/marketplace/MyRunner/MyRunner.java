package com.course.marketplace.MyRunner;

import com.course.marketplace.dto.CourseDto;
import com.course.marketplace.dto.StudentDto;
import com.course.marketplace.dto.TutorDto;
import com.course.marketplace.dto.UserDetailsDto;
import com.course.marketplace.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Component
public class MyRunner {

  private RoleService roleService;

    private UserDetailsService userDetailsService;

    private TutorService tutorService;

    private CourseService courseService;

    private StudentService studentService;

  //  @Override
    public void run(String... args) throws Exception {
        //createRoles();
        //createAdmin();
      //createTutors();
        //createCourses();
        //StudentDto student=createStudent();
        //assignCourseToStudent(student);
    }




    private void createRoles() {
        Arrays.asList("Admin","Tutor","Student").forEach(role->roleService.createRole(role));
    }
    private void createAdmin() {
        userDetailsService.createUser("bansi32","bansi32@gmail.com","bansi@123");
        userDetailsService.assignRoleToUser("bansi32@gmail.com","Admin");
    }
    private void createTutors()
    {
        TutorDto tutorDto1=new TutorDto();
        tutorDto1.setFName("Jitu");
        tutorDto1.setLName("Patel");
        tutorDto1.setActive(true);
        tutorDto1.setSummary("FrontEnd Developer");
        UserDetailsDto userDetailsDto1=new UserDetailsDto();
        userDetailsDto1.setUserName("jitu24");
        userDetailsDto1.setEmail("jitu24@gmail.com");
        userDetailsDto1.setPassword("jitu@123");
        tutorDto1.setUserDetailsDto(userDetailsDto1);
        tutorService.createTutor(tutorDto1);
    }
    private void createCourses() {
        CourseDto courseDto=new CourseDto();
        courseDto.setCourseDescription("ReactJs FrontEnd Course with 2 projects");
        courseDto.setCourseName("ReactJs");
        courseDto.setCourseDuration("12 hrs");
        TutorDto tutorDto=new TutorDto();
        tutorDto.setTutorId(2L);
        courseDto.setTutorDto(tutorDto);
        courseService.createCourse(courseDto);

    }
    private StudentDto createStudent() {
        StudentDto studentDto=new StudentDto();
        studentDto.setFName("Yug");
        studentDto.setLName("Patel");
        studentDto.setLevel("Assistant");
        UserDetailsDto userDetailsDto=new UserDetailsDto();
        userDetailsDto.setUserName("yug14");
        userDetailsDto.setEmail("yug14@gmail.com");
        userDetailsDto.setPassword("yug@1234");
        studentDto.setUserDetailsDto(userDetailsDto);
        return studentService.createStudent(studentDto);
    }
    private void assignCourseToStudent(StudentDto student) {
        courseService.assignStudentToCourse(2L,student.getStudentId());
    }

}
