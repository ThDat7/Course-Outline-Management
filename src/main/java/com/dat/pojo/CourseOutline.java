/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(columnDefinition = "TEXT")
    private String content;

    private int yearPublished;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "status", length = 15, columnDefinition = "varchar(15) default 'DOING'")
    @Enumerated(EnumType.STRING)
    private OutlineStatus status;

    @OneToMany(mappedBy = "courseOutline", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseAssessment> courseAssessments;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadlineDate;

    @OneToMany(mappedBy = "courseOutline", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "courseOutline")
    private Set<EducationProgramCourse> educationProgramCourses;
}
