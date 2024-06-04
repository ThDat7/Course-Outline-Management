package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EducationProgramCourseDto {
    private Integer courseOutlineId;
    private String courseName;
    private Integer semester;
}
