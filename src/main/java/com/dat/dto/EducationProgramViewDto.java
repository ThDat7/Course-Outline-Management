package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EducationProgramViewDto {
    private Integer id;
    private String majorName;
    private Integer schoolYear;
    private List<EducationProgramCourseDto> educationProgramCourses;
}
