package com.course.marketplace.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {
    private Long courseId;
    private String courseName;
    private String courseDuration;
    private String courseDescription;
    private String courseThumbnail;
    private TutorDto tutorDto;

}
