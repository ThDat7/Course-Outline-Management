package com.dat.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EducationProgramId implements Serializable {
    @Column(name = "major_id")
    private Integer majorId;

    @Column(name = "course_id")
    private Integer courseId;
}
