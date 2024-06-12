package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeacherInfoDto {
    private Integer id;
    @NotNull
    @Size(max = 50)
    private String username;
    private String password;
    @NotNull
    @Size(min = 5, max = 50)
    private String firstName;
    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;
    @NotNull
    @Size(min = 3, max = 50)
    private String email;
    private String phone;
    @NotNull
    @Min(1)
    private Integer majorId;
    private MultipartFile avatar;
    private String image;
}
