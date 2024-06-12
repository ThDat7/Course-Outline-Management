package com.dat.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseOutlineEditRequestDto {
    @NotNull(message = "{courseOutline.content.nullErr}")
    private String content;
    @Valid
    private List<CourseAssessmentDto> courseAssessments = new ArrayList<>();
    @NotNull(message = "{courseOutline.status.nullErr}")
    private String status;
}
