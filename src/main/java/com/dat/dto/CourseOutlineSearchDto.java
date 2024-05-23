package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseOutlineSearchDto {
    private int id;
    private String courseName;
    private String teacherName;
    private int courseCredits;
    private List<Integer> years;
}
