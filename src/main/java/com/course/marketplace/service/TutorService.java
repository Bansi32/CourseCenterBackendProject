package com.course.marketplace.service;

import com.course.marketplace.dto.TutorDto;
import com.course.marketplace.entity.Tutor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TutorService {
    Tutor getTutorById(Long tutorId);
    Page<TutorDto> findTutorsByName(String name,int page,int size);
    TutorDto getTutorByEmail(String email);
    TutorDto createTutor(TutorDto tutorDto);
    TutorDto updateTutor(TutorDto tutorDto);
    List<TutorDto> fetchTutor();
    void removeTutor(Long tutorId);
    List<TutorDto> fetchInActivatedTutor();
    List<TutorDto> fetchActivatedTutor();
}
