package com.dat.service;

import com.dat.pojo.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService extends BaseService<Teacher, Integer> {

    List<Teacher> getAll();

    void teacherRegister(Teacher teacherDto2User, Integer majorId, MultipartFile avatar);

    Teacher getProfile();

    void additionalInfo(Teacher teacherInfoDto2Entity, MultipartFile avatar);

    void updateProfile(Teacher teacher, MultipartFile avatar);

    void acceptPending(Teacher teacher, MultipartFile avatar);

    boolean addOrUpdate(Teacher teacher, MultipartFile avatar);
}
