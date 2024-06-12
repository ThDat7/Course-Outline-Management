/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author DELL
 */
@Entity
@Table(name = "facultys")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "{faculty.name.nullErr}")
    @Size(min = 2, max = 50, message = "{faculty.name.lengthErr}")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "{faculty.alias.nullErr}")
    @NotBlank(message = "{faculty.alias.blankErr}")
    private String alias;

    @OneToMany(mappedBy = "faculty")
    private Set<Major> majors;

    public Faculty(Integer id) {
        this.id = id;
    }
}
