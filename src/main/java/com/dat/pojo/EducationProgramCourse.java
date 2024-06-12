package com.dat.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "education_program_courses",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"course_id", "education_program_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationProgramCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Min(value = 1, message = "{educationProgramCourse.semester.minErr}")
    @Max(value = 16, message = "{educationProgramCourse.semester.maxErr}")
    @NotNull(message = "{educationProgramCourse.semester.nullErr}")
    private int semester;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @NotNull(message = "{educationProgramCourse.course.nullErr}")
    private Course course;


    @ManyToOne
    @JoinColumn(name = "education_program_id", nullable = false)
    @NotNull(message = "{educationProgramCourse.educationProgram.nullErr}")
    private EducationProgram educationProgram;

    @ManyToOne
    @JoinColumn(name = "course_outline_id")
    private CourseOutline courseOutline;
}
