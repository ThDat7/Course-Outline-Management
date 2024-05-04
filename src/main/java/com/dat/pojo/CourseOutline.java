/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import java.util.Set;
import javax.persistence.*;

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
    @OneToMany(mappedBy = "courseOutline", fetch = FetchType.EAGER)
    private Set<CourseOutlineDetail> courseOutlineDetails;
    @OneToMany(mappedBy = "courseOutline")
    private Set<CourseAssessment> courseAssessments;
    @OneToMany(mappedBy = "courseOutline")
    private Set<Comment> comments;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "assign_outline_id",
            referencedColumnName = "id")
    private AssignOutline assignOutline;
}
