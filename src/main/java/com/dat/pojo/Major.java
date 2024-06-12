/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
@Table(name = "majors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 3, max = 50, message = "{major.name.lengthErr}")
    @Column(nullable = false)
    @NotNull(message = "{major.name.nullErr}")
    private String name;

    @NotNull(message = "{major.alias.nullErr}")
    @NotBlank(message = "{major.alias.blankErr}")
    @Column(nullable = false)
    private String alias;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    @NotNull(message = "{major.faculty.nullErr}")
    private Faculty faculty;

    @OneToMany(mappedBy = "major")
    private Set<EducationProgram> educationPrograms;

    public Major(Integer id) {
        this.id = id;
    }
}
