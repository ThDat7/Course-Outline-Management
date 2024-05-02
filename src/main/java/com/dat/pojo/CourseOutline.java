/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author DELL
 */
@Entity
@Table(name = "course_outlines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseOutline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @OneToMany(mappedBy = "courseOutline")
    private Set<CourseOutlineDetail> courseOutlineDetails;
    @OneToMany(mappedBy = "courseOutline")
    private Set<CourseAssessment> courseAssessments;
    @OneToMany(mappedBy = "courseOutline")
    private Set<Comment> comments;
    @OneToOne
    @JoinColumn(
            name = "assign_outline_id",
            referencedColumnName = "id")
    private AssignOutline assignOutline;
}
