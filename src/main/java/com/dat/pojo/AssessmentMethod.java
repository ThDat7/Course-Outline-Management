package com.dat.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    private String method;
    private String time;
    private String clos;
    private Integer weightPercent;

    @ManyToOne
    @JoinColumn(name = "course_assessment_id", referencedColumnName = "id")
    private CourseAssessment courseAssessment;
}
