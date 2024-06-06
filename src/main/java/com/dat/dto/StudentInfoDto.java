package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentInfoDto {
    private Integer id;
    private String studentCode;
    private String firstName;
    private String lastName;
    private String email;
    private String schoolYear;
    private Integer majorId;
    private String image;
}
