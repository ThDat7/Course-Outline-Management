package com.dat.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    private int semester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "education_program_id")
    private EducationProgram educationProgram;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_outline_id")
    private CourseOutline courseOutline;
}
