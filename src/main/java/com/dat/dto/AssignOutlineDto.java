package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignOutlineDto {
    private int educationProgramCourseOutlineId;
    private int courseOutlineId;
    private String courseName;
    private String majorName;
    private int yearPublished;

    public AssignOutlineDto(int educationProgramCourseOutlineId, String courseName, String majorName) {
        this.educationProgramCourseOutlineId = educationProgramCourseOutlineId;
        this.courseName = courseName;
        this.majorName = majorName;
    }
}
