/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "course_outline_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseOutlineDetail {

    @EmbeddedId
    private CourseOutlineDetailId id;

    @ManyToOne
    @MapsId("courseOutlineId")
    private CourseOutline courseOutline;
}
