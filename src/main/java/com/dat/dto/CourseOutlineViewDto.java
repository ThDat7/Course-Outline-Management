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
public class CourseOutlineViewDto {
    private int id;
    private String courseName;
    private String content;
    private String teacherName;
    private List<Integer> years;
    private List<CourseAssessmentDto> courseAssessments;
    private List<CommentDto> comments;
}
