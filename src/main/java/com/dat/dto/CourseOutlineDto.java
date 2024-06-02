package com.dat.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseOutlineDto {
    private int id;
    private String courseName;
    private String content;
    private List<CourseAssessmentDto> courseAssessments = new ArrayList<>();
    private String status;
    private Date deadlineDate;
}
