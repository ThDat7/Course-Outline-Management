package com.dat.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "education_programs",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"major_id", "schoolYear"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int schoolYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "major_id")
    private Major major;

    @OneToMany(mappedBy = "educationProgram", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EducationProgramCourse> educationProgramCourses;
}
