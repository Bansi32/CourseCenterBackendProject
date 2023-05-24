package com.course.marketplace.mapper;

import com.course.marketplace.dto.CourseDto;
import com.course.marketplace.dto.TutorDto;
import com.course.marketplace.entity.Course;
import com.course.marketplace.entity.Tutor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper {
    @Autowired
    private TutorMapper tutorMapper;


    public CourseDto fromCourse(Course course)
    {
        CourseDto courseDto=new CourseDto();
        //copying from courseDto obj to course obj
        BeanUtils.copyProperties(course,courseDto);
        courseDto.setTutorDto(tutorMapper.fromTutor(course.getTutor()));
        return courseDto;
    }

    public Course fromCourseDto(CourseDto courseDto)
    {
        Course course=new Course();
        //copying from course obj to courseDto obj
        BeanUtils.copyProperties(courseDto,course);
        return course;
    }
}
