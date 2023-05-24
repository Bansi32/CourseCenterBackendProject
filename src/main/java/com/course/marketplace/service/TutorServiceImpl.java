package com.course.marketplace.service;

import com.course.marketplace.dao.TutorDao;
import com.course.marketplace.dto.TutorDto;
import com.course.marketplace.entity.Course;
import com.course.marketplace.entity.Tutor;
import com.course.marketplace.entity.UserDetails;
import com.course.marketplace.mapper.TutorMapper;
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
public class TutorServiceImpl implements TutorService{
    @Autowired
    private TutorDao tutorDao;
    @Autowired
    private TutorMapper tutorMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CourseService courseService;
    @Override
    public Tutor getTutorById(Long tutorId) {
        return tutorDao.findById(tutorId).orElseThrow(()->new EntityNotFoundException("Tutor Id "+tutorId+" not found!"));
    }

    @Override
    public Page<TutorDto> findTutorsByName(String name, int page, int size) {
        PageRequest pageRequest=PageRequest.of(page,size);
        Page<Tutor> tutorsPage=tutorDao.findTutorsByName(name,pageRequest);
        return new PageImpl<>(tutorsPage.getContent().stream().map(tutor->tutorMapper.fromTutor(tutor)).collect(Collectors.toList()),pageRequest,tutorsPage.getTotalElements());
    }

    @Override
    public TutorDto getTutorByEmail(String email) {
       return tutorMapper.fromTutor(tutorDao.findTutorByEmail(email));
    }

    @Override
    public TutorDto createTutor(TutorDto tutorDto) {
        UserDetails userDetails=userDetailsService.createUser(tutorDto.getUserDetailsDto().getUserName(),tutorDto.getUserDetailsDto().getEmail(),tutorDto.getUserDetailsDto().getPassword());
        userDetailsService.assignRoleToUser(userDetails.getEmail(),"Tutor");
        Tutor tutor=tutorMapper.fromTutorDto(tutorDto);
        tutor.setUserDetails(userDetails);
        Tutor savedTutor= tutorDao.save(tutor);
        return tutorMapper.fromTutor(savedTutor);
    }

    @Override
    public TutorDto updateTutor(TutorDto tutorDto) {

        Tutor getTutor=getTutorById(tutorDto.getTutorId());
        Tutor tutor=tutorMapper.fromTutorDto(tutorDto);
        tutor.setUserDetails(getTutor.getUserDetails());
        tutor.setCourses(getTutor.getCourses());
        Tutor updatedTutor=tutorDao.save(tutor);
        return tutorMapper.fromTutor(updatedTutor);
    }

    @Override
    public List<TutorDto> fetchTutor() {
        return tutorDao.findAll().stream().map(tutor -> tutorMapper.fromTutor(tutor)).collect(Collectors.toList());
    }

    @Override
    public void removeTutor(Long tutorId) {
        Tutor tutor=getTutorById(tutorId);
        for(Course course:tutor.getCourses())
        {
            courseService.removeCourse(course.getCourseId());
        }
        tutorDao.deleteById(tutorId);
    }

    @Override
    public List<TutorDto> fetchInActivatedTutor() {
        return tutorDao.getAllInActiveTutor().stream().map(tutor -> tutorMapper.fromTutor(tutor)).collect(Collectors.toList());
    }
    @Override
    public List<TutorDto> fetchActivatedTutor() {
        return tutorDao.getAllActiveTutor().stream().map(tutor -> tutorMapper.fromTutor(tutor)).collect(Collectors.toList());
    }
}
