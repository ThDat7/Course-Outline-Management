package com.dat.service;

import com.dat.dto.StudentInfoDto;
import com.dat.pojo.Student;
import com.dat.pojo.User;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {
    void studentRegister(Student student);

    Student getProfile();

    void additionalStudentInfo(MultipartFile avatar);

    void updateProfile(Student student, MultipartFile avatar);
}
