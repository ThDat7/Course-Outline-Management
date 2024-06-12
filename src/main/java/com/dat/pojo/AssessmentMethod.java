package com.dat.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "assessment_methods")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "{assessmentMethod.method.nullErr}")
    @Size(min = 2, message = "{assessmentMethod.method.lengthErr}")
    private String method;

    @Column(nullable = false)
    @NotNull(message = "{assessmentMethod.time.nullErr}")
    @Size(min = 2, message = "{assessmentMethod.time.lengthErr}")
    private String time;

    @Column(nullable = false)
    @NotNull(message = "{assessmentMethod.clos.nullErr}")
    @Size(min = 2, message = "{assessmentMethod.clos.lengthErr}")
    private String clos;

    @Column(nullable = false)
    @NotNull(message = "{assessmentMethod.weightPercent.nullErr}")
    @Min(value = 1, message = "{assessmentMethod.weightPercent.minErr}")
    @Max(value = 100, message = "{assessmentMethod.weightPercent.maxErr}")
    private Integer weightPercent;

    @ManyToOne
    @JoinColumn(name = "course_assessment_id", referencedColumnName = "id")
    private CourseAssessment courseAssessment;
}
