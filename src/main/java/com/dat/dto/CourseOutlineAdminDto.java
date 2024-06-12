package com.dat.dto;

import com.dat.pojo.Course;
import com.dat.pojo.OutlineStatus;
import com.dat.pojo.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseOutlineAdminDto {
    private Integer id;
    private String content;
    private int yearPublished;
    private CourseDto course;
    private TeacherDto teacher;
    private OutlineStatus status;
    private List<CourseAssessmentDto> courseAssessments = new ArrayList<>();
    private Date deadlineDate;
}
