package com.dat.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{student.studentCode.nullErr}")
    @Size(min = 5, max = 50, message = "{student.studentCode.lengthErr}")
    @Column(nullable = false)
    private String studentCode;

    @Column(nullable = false)
    @NotNull(message = "{student.schoolYear.nullErr}")
    @Min(value = 2000, message = "{student.schoolYear.minErr}")
    private Integer schoolYear;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "{student.user.nullErr}")
    @Valid
    private User user;

    @ManyToOne
    @JoinColumn(name = "major_id", nullable = false)
    @NotNull(message = "{student.major.nullErr}")
    private Major major;
}
