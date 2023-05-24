package com.course.marketplace.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {
    private Long studentId;
      private String fName;
    private String lName;
    private String level;
    private UserDetailsDto userDetailsDto;
}
