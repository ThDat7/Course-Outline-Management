/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

/**
 * @author DELL
 */
@Entity
@Table(name = "course_assessments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;

    @OneToMany(mappedBy = "courseAssessment", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AssessmentMethod> assessmentMethods;

    @ManyToOne
    @JoinColumn(name = "course_outline_id", nullable = false)
    private CourseOutline courseOutline;
}
