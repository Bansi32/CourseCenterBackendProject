package com.course.marketplace.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TutorDto {
    private Long tutorId;
    private String fName;
    private String lName;
    private boolean isActive;
    private String summary;
    private UserDetailsDto userDetailsDto;

}
