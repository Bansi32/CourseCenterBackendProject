package com.course.marketplace.service;

import com.course.marketplace.dao.CourseDao;
import com.course.marketplace.dao.StudentDao;
import com.course.marketplace.dao.TutorDao;
import com.course.marketplace.dto.CourseDto;
import com.course.marketplace.entity.Course;
import com.course.marketplace.entity.Student;
import com.course.marketplace.entity.Tutor;
import com.course.marketplace.mapper.CourseMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TutorDao tutorDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public Course getCourseById(Long courseId) {
        return courseDao.findById(courseId).orElseThrow(()->new EntityNotFoundException("Course with ID "+courseId+" not found!"));
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto)
    {
        Course course=courseMapper.fromCourseDto(courseDto);
        Tutor tutor=tutorDao.findById(
                courseDto.getTutorDto().getTutorId()).orElseThrow(()->new EntityNotFoundException("Tutor with ID "+courseDto.getTutorDto().getTutorId()+" Not Found"));
        course.setTutor(tutor);
        Course savedCourse= courseDao.save(course);
        return courseMapper.fromCourse(savedCourse) ;
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) {
        Course getCourse=getCourseById(courseDto.getCourseId());
        Tutor tutor =tutorDao.findById(courseDto.getTutorDto().getTutorId()).orElseThrow(()->new EntityNotFoundException("Tutor with ID "+courseDto.getTutorDto().getTutorId()+" Not Found"));
        Course course=courseMapper.fromCourseDto(courseDto);
        course.setTutor(tutor);
        course.setStudents(getCourse.getStudents());
        Course updatedCourse=courseDao.save(course);
        return courseMapper.fromCourse(updatedCourse);
    }

    @Override
    public Page<CourseDto> findCoursesByCourseName(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> coursesPage = courseDao.findCoursesByCourseNameContains(keyword, pageRequest);
        return new PageImpl<>(coursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, coursesPage.getTotalElements());
    }

    @Override
    public void assignStudentToCourse(Long courseId, Long studentId) {
        Student student =studentDao.findById(studentId).orElseThrow(()->new EntityNotFoundException("Student with ID "+studentId+" not found!"));
        Course course=getCourseById(courseId);
        course.assignStudentToCourse(student);
    }

    @Override
    public Page<CourseDto> fetchCoursesForStudent(Long studentId, int page, int size)
    {
        PageRequest pageRequest=PageRequest.of(page,size);
        Page<Course> studentCoursesPage=courseDao.getCoursesByStudentId(studentId,pageRequest);
        return new PageImpl<>(studentCoursesPage.getContent().stream().map(course->courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest,studentCoursesPage.getTotalPages());
    }

    @Override
    public Page<CourseDto> fetchNonEnrolledInCoursesForStudent(Long studentId, int page, int size) {

        PageRequest pageRequest=PageRequest.of(page,size);
        Page<Course> nonEnrolledInCoursePage=courseDao.getNonEnrolledInCoursesByStudentId(studentId,pageRequest);
        return new PageImpl<>(nonEnrolledInCoursePage.getContent().stream().map(course->courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest,nonEnrolledInCoursePage.getTotalPages());
    }

    @Override
    public void removeCourse(Long courseId)
    {
        courseDao.deleteById(courseId);
    }

    @Override
    public Page<CourseDto> fetchCoursesForTutor(Long tutorId, int page, int size) {

        PageRequest pageRequest=PageRequest.of(page,size);
        Page<Course> tutorCoursesPage=courseDao.getCoursesByTutorId(tutorId,pageRequest);
        return new PageImpl<>(tutorCoursesPage.getContent().stream().map(course->courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest,tutorCoursesPage.getTotalPages());

    }
}
