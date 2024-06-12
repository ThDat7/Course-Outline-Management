package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentMethodDto {
    @NotNull(message = "{assessmentMethod.method.nullErr}")
    @Size(min = 2, message = "{assessmentMethod.method.lengthErr}")
    private String method;
    @NotNull(message = "{assessmentMethod.time.nullErr}")
    @Size(min = 2, message = "{assessmentMethod.time.lengthErr}")
    private String time;
    @NotNull(message = "{assessmentMethod.clos.nullErr}")
    @Size(min = 2, message = "{assessmentMethod.clos.lengthErr}")
    private String clos;
    @NotNull(message = "{assessmentMethod.weightPercent.nullErr}")
    @Min(value = 1, message = "{assessmentMethod.weightPercent.minErr}")
    @Max(value = 100, message = "{assessmentMethod.weightPercent.maxErr}")
    private Integer weightPercent;
}
