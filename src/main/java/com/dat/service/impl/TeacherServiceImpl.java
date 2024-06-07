package com.dat.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dat.pojo.*;
import com.dat.repository.BaseRepository;
import com.dat.repository.TeacherRepository;
import com.dat.service.TeacherService;
import com.dat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TeacherServiceImpl
        extends BaseServiceImpl<Teacher, Integer>
        implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    public TeacherServiceImpl(BaseRepository<Teacher, Integer> repository) {
        super(repository);
    }

    public List<Teacher> getAll() {
        return teacherRepository.getAll();
    }

    @Override
    public void teacherRegister(Teacher teacher, Integer majorId, MultipartFile avatar) {
        if (avatar != null && avatar.getSize() > 0) {
            try {
                Map res = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                teacher.setId(null);
                teacher.getUser().setImage(res.get("secure_url").toString());
                teacher.getUser().setId(null);
                teacher.getUser().setPassword(passwordEncoder.encode(teacher.getUser().getPassword()));
                teacher.getUser().setStatus(UserStatus.PENDING);
                teacher.getUser().setRole(UserRole.TEACHER);
                teacher.setMajor(new Major(majorId));
                teacherRepository.addOrUpdate(teacher);
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    @Override
    public Teacher getProfile() {
        Integer currentUserId = userService.getCurrentUser().getId();

        Teacher teacher = teacherRepository.getByUserId(currentUserId);
        teacher.getUser().setPassword("");
        return teacher;
    }

    @Override
    public void additionalInfo(Teacher teacher, MultipartFile avatar) {
        Integer currentUserId = userService.getCurrentUser().getId();
        Teacher oldTeacher = teacherRepository.getByUserIdAndUserStatus(currentUserId, UserStatus.NEED_INFO);
        teacher.getUser().setStatus(UserStatus.ENABLED);
        teacherRepository.addOrUpdate(teacher);
        userService.updateCurrentUserInfo(teacher.getUser(), avatar);
    }

    @Override
    public void updateProfile(Teacher teacher, MultipartFile avatar) {
        userService.updateCurrentUserInfo(teacher.getUser(), avatar);
    }
}
