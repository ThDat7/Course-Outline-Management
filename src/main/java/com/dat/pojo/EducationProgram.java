package com.dat.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @Column(nullable = false)
    @NotNull(message = "{educationProgram.schoolYear.nullErr}")
    @Min(value = 2000, message = "{educationProgram.schoolYear.minErr}")
    private int schoolYear;

    @ManyToOne
    @JoinColumn(name = "major_id", nullable = false)
    @NotNull(message = "{educationProgram.major.nullErr}")
    private Major major;

    @OneToMany(mappedBy = "educationProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EducationProgramCourse> educationProgramCourses;
}
