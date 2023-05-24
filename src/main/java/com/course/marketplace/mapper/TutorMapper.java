package com.course.marketplace.mapper;

import com.course.marketplace.dto.TutorDto;
import com.course.marketplace.entity.Tutor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TutorMapper {
    public TutorDto fromTutor(Tutor tutor)
    {
        TutorDto tutorDto=new TutorDto();
        //copying from tutorDto obj to tutor obj
        BeanUtils.copyProperties(tutor,tutorDto);
        return tutorDto;
    }

    public Tutor fromTutorDto(TutorDto tutorDto)
    {
        Tutor tutor=new Tutor();
        //copying from tutor obj to tutorDto obj
        BeanUtils.copyProperties(tutorDto,tutor);
        return tutor;
    }
}
