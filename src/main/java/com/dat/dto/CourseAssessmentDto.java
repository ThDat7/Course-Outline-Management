package com.dat.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseAssessmentDto {
    @NotNull(message = "{courseAssessment.type.nullErr}")
    @Size(min = 2, message = "{courseAssessment.type.lengthErr}")
    private String type;
    @Valid
    private List<AssessmentMethodDto> assessmentMethods;
}
