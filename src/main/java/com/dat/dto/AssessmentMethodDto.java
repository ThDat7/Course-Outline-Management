package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentMethodDto {
    private String method;
    private String time;
    private String clos;
    private Integer weightPercent;
}
