package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeacherSearchDto {
    private Integer id;
    private String fullName;
    private String avatar;
    private String majorName;
}
