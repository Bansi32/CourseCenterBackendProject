package com.course.marketplace.controller;

import com.course.marketplace.dto.CourseDto;
import com.course.marketplace.dto.StudentDto;
import com.course.marketplace.entity.UserDetails;
import com.course.marketplace.service.CourseService;
import com.course.marketplace.service.StudentService;
import com.course.marketplace.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@CrossOrigin("*")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserDetailsService userDetailsService;
    @GetMapping
    public ResponseEntity<Page<StudentDto>> searchStudents(@RequestParam(name="keyword",defaultValue = "")String keyword, @RequestParam(value = "page",defaultValue = "0")int page, @RequestParam(name="size",defaultValue = "2")int size)
    {
        ResponseEntity responseEntity;
        try{
            responseEntity=new ResponseEntity(studentService.getStudentsByName(keyword, page, size) , HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId)
    {
        ResponseEntity responseEntity;
        try{
            studentService.removeStudent(studentId);
            responseEntity=new ResponseEntity("Student deleted successfully!" , HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PostMapping
    public ResponseEntity<StudentDto> saveStudent(@RequestBody StudentDto studentDto)
    {
        ResponseEntity responseEntity;
        try{
            UserDetails userDetails=userDetailsService.getUserByEmail(studentDto.getUserDetailsDto().getEmail());
            if(userDetails!=null)
            {
                throw new RuntimeException("Email Already Exists!");
            }

            responseEntity=new ResponseEntity( studentService.createStudent(studentDto) , HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto,@PathVariable Long studentId)
    {
        ResponseEntity responseEntity;
        try{
            studentDto.setStudentId(studentId);
            responseEntity=new ResponseEntity(studentService.updateStudent(studentDto) , HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<Page<CourseDto>> coursesByStudentId(@PathVariable Long studentId,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "2")int size)
    {
        ResponseEntity responseEntity;
        try{
            responseEntity=new ResponseEntity(courseService.fetchCoursesForStudent(studentId, page, size) , HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/{studentId}/other-courses")
    public ResponseEntity<Page<CourseDto>> nonSubscribedCoursesByStudentId(@PathVariable Long studentId,@RequestParam(name="page",defaultValue = "0")int page,@RequestParam(name="size",defaultValue = "2")int size)
    {
        ResponseEntity responseEntity;
        try{
            responseEntity=new ResponseEntity(courseService.fetchNonEnrolledInCoursesForStudent(studentId, page, size) , HttpStatus.OK);
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }



}
