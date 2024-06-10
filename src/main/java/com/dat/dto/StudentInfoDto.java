package com.dat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentInfoDto {
    private Integer id;
    private String username;
    private String password;
    private String studentCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer schoolYear;
    private Integer majorId;
    private MultipartFile avatar;
    private String image;
}
