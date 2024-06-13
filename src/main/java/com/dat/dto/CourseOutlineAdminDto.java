package com.dat.dto;

import com.dat.pojo.Course;
import com.dat.pojo.OutlineStatus;
import com.dat.pojo.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @Min(value = 2000, message = "{courseOutline.yearPublished.minErr}")
    private Integer yearPublished;
    private CourseDto course;
    private TeacherDto teacher;
    @NotNull(message = "{courseOutline.status.nullErr}")
    private OutlineStatus status;
    @Valid
    private List<CourseAssessmentDto> courseAssessments = new ArrayList<>();
    @NotNull(message = "{courseOutline.deadlineDate.nullErr}")
    private Date deadlineDate;
    private Integer epcId;
}
