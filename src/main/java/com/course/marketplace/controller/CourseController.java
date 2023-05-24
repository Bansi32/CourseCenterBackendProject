package com.course.marketplace.controller;

import com.course.marketplace.dto.CourseDto;
import com.course.marketplace.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/courses")
@CrossOrigin("*")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @GetMapping
    public Page<CourseDto> searchCourses(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "2") int size) {
        return courseService.findCoursesByCourseName(keyword, page, size);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Long courseId)
    {
        courseService.removeCourse(courseId);
    }
    @PostMapping
    public CourseDto saveCourse(@RequestBody CourseDto courseDto)
    {
       return courseService.createCourse(courseDto);
    }

    @PutMapping("/{courseId}")
    public CourseDto updateCourse(@RequestBody CourseDto courseDto,@PathVariable Long courseId)
    {

           courseDto.setCourseId(courseId);
           return courseService.updateCourse(courseDto);
    }

    @PostMapping("/{courseId}/enroll/students/{studentId}")
    public void enrollStudentInCourse(@PathVariable Long courseId,@PathVariable Long studentId)
    {
            courseService.assignStudentToCourse(courseId,studentId);
    }


}
