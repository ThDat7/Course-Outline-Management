package com.dat.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "education_programs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationProgram {
    @EmbeddedId
    private EducationProgramId id;
    
    private int semester;

    @ManyToOne
    @MapsId("courseId")
    private Course course;


    @ManyToOne
    @MapsId("majorId")
    private Major major;
}
