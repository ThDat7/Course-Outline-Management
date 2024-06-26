/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
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

    @Min(value = 2000, message = "{courseOutline.yearPublished.minErr}")
    private int yearPublished;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @NotNull(message = "{courseOutline.course.nullErr}")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "status", length = 15, columnDefinition = "varchar(15) default 'DOING'", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{courseOutline.status.nullErr}")
    private OutlineStatus status;

    @OneToMany(mappedBy = "courseOutline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseAssessment> courseAssessments;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    @NotNull(message = "{courseOutline.deadlineDate.nullErr}")
    private Date deadlineDate;

    @OneToMany(mappedBy = "courseOutline", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "courseOutline")
    private Set<EducationProgramCourse> educationProgramCourses;

    public CourseOutline(int id) {
        this.id = id;
    }
}
