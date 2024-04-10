/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author DELL
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class CourseOutlineDetailId implements Serializable {

    @Column(name = "course_outline_id")
    private Integer courseOutlineId;

    @Column(name = "school_year")
    private Integer schoolYear;
}
