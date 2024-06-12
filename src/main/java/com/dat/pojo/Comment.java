/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import java.time.LocalTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "{comment.user.nullErr}")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_outline_id", nullable = false)
    private CourseOutline courseOutline;

    @Column(nullable = false)
    @NotNull(message = "{comment.cmt.nullErr}")
    @Size(min = 2, message = "{comment.cmt.lengthErr}")
    private String cmt;

    private LocalTime time;
}
