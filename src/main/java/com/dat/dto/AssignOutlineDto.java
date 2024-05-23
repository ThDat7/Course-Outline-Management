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
    private int id;
    private String courseName;
    private String assignDate;
    private String deadlineDate;
    private String courseStatus;
}
