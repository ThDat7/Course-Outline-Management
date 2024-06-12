package com.dat.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dat.pojo.*;
import com.dat.repository.BaseRepository;
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
public class StudentServiceImpl extends BaseServiceImpl<Student, Integer> implements StudentService {
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

    public StudentServiceImpl(BaseRepository<Student, Integer> repository) {
        super(repository);
    }

    @Override
    public void studentRegister(Student student) {
        student.setId(null);
        student.getUser().setId(null);
        student.getUser().setStatus(UserStatus.PENDING);
        student.getUser().setRole(UserRole.STUDENT);
        student.getUser().setUsername("");
        student.getUser().setPassword("");
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
        User oldUserPending = userRepository.findByIdAndStatus(currentUserId, UserStatus.NEED_INFO);
        if (oldUserPending == null)
            throw new RuntimeException("Your account is not in additional info status");

        try {
            userService.uploadAvatar(oldUserPending, avatar);
            oldUserPending.setStatus(UserStatus.ENABLED);
            userRepository.addOrUpdate(oldUserPending);
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

    @Override
    public boolean addOrUpdate(Student student, MultipartFile avatar) {
        User updateUser = null;
        try {
            updateUser = userService.addOrUpdate(student.getUser(), avatar);
            student.setUser(updateUser);
            return studentRepository.addOrUpdate(student);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void acceptPendingStudent(Student student, MultipartFile avatar) {
        User oldUserPending = userRepository.findByIdAndStatus(student.getUser().getId(), UserStatus.PENDING);
        if (oldUserPending == null)
            throw new RuntimeException("User is not in pending status");
        try {
            student.getUser().setStatus(UserStatus.ENABLED);
            User updateUser = userService.addOrUpdate(student.getUser(), avatar);
            student.setUser(updateUser);
            studentRepository.addOrUpdate(student);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
