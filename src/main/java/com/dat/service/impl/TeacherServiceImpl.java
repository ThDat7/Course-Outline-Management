package com.dat.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dat.pojo.*;
import com.dat.repository.BaseRepository;
import com.dat.repository.TeacherRepository;
import com.dat.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    public TeacherServiceImpl(BaseRepository<Teacher, Integer> repository) {
        super(repository);
    }

    public List<Teacher> getAll() {
        return teacherRepository.getAll();
    }

    @Override
    public void teacherRegister(Teacher teacher, Integer majorId, MultipartFile avatar) {
        if (avatar == null || avatar.getSize() == 0)
            throw new RuntimeException("Avatar is required");

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
        User oldUserPending = userRepository.findByIdAndStatus(currentUserId, UserStatus.NEED_INFO);
        if (oldUserPending == null)
            throw new RuntimeException("Your account is not in addition info status");

        Teacher oldTeacher = teacherRepository.getByUserId(currentUserId);
        oldTeacher.getUser().setStatus(UserStatus.ENABLED);
        teacherRepository.addOrUpdate(oldTeacher);
        userService.updateCurrentUserInfo(teacher.getUser(), avatar);
    }

    @Override
    public void updateProfile(Teacher teacher, MultipartFile avatar) {
        userService.updateCurrentUserInfo(teacher.getUser(), avatar);
    }

    @Override
    public boolean addOrUpdate(Teacher teacher, MultipartFile avatar) {
        User updateUser = null;
        try {
            updateUser = userService.addOrUpdate(teacher.getUser(), avatar);
            teacher.setUser(updateUser);
            return teacherRepository.addOrUpdate(teacher);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Teacher> search(String keyword, List<Integer> excludedIds) {
        return teacherRepository.search(keyword, excludedIds, 5);
    }

    @Override
    public void acceptPending(Teacher teacher, MultipartFile avatar) {
        User oldUserPending = userRepository.findByIdAndStatus(teacher.getUser().getId(), UserStatus.PENDING);
        if (oldUserPending == null)
            throw new RuntimeException("User is not in pending status");

        try {
            teacher.getUser().setStatus(UserStatus.ENABLED);
            User updateUser = userService.addOrUpdate(teacher.getUser(), avatar);
            teacher.setUser(updateUser);
            addOrUpdate(teacher);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
