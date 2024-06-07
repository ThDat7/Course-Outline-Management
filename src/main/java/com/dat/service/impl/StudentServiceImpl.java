package com.dat.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dat.pojo.Student;
import com.dat.pojo.User;
import com.dat.pojo.UserRole;
import com.dat.pojo.UserStatus;
import com.dat.repository.StudentRepository;
import com.dat.repository.UserRepository;
import com.dat.service.StudentService;
import com.dat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserService userService;

    @Override
    public void studentRegister(Student student) {
        student.setId(null);
        student.getUser().setId(null);
        student.getUser().setStatus(UserStatus.PENDING);
        student.getUser().setRole(UserRole.STUDENT);
        student.getUser().setUsername(null);
        student.getUser().setPassword(null);
        studentRepository.add(student);
    }

    @Override
    public Student getProfile() {
        Integer currentUserId = userService.getCurrentUser().getId();

        Student student = studentRepository.getByUserId(currentUserId);
        student.getUser().setPassword("");
        return student;
    }

    @Override
    public void additionalStudentInfo(MultipartFile avatar) {
        Integer currentUserId = userService.getCurrentUser().getId();
        User oldUser = userRepository.getById(currentUserId);
        try {
            userService.uploadAvatar(oldUser, avatar);
            oldUser.setStatus(UserStatus.ENABLED);
            userRepository.addOrUpdate(oldUser);
        } catch (IOException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateProfile(Student student, MultipartFile avatar) {
        Integer currentUserId = userService.getCurrentUser().getId();

        Student oldStudent = studentRepository.getByUserId(currentUserId);

        oldStudent.setStudentCode(student.getStudentCode());
        studentRepository.update(oldStudent);
        userService.updateCurrentUserInfo(student.getUser(), avatar);
    }
}
