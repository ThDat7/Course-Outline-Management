/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import java.util.Date;
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
@Table(name = "assign_outlines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignOutline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToOne(mappedBy = "assignOutline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CourseOutline courseOutline;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date assignDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date deadlineDate;

    public AssignOutline(Integer id) {
        this.id = id;
    }
}
