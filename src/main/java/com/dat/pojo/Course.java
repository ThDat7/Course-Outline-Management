/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author DELL
 */
@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "{course.name.nullErr}")
    @Size(min = 3, max = 255, message = "{course.name.lengthErr}")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "{course.code.nullErr}")
    @Size(min = 3, max = 50, message = "{course.code.lengthErr}")
    private String code;

    @Column(nullable = false)
    @NotNull(message = "{course.credits.nullErr}")
    @Min(value = 1, message = "{course.credits.minErr}")
    private Integer credits;

    @OneToMany(mappedBy = "course")
    private Set<CourseOutline> courseOutlines;

    @OneToMany(mappedBy = "course")
    private Set<EducationProgramCourse> educationProgramCourses;

    public Course(Integer id) {
        this.id = id;
    }
}
