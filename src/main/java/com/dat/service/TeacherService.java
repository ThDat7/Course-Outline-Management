package com.dat.service;

import com.dat.pojo.Teacher;

import java.util.List;

public interface TeacherService extends BaseService<Teacher, Integer> {

    List<Teacher> getAll();
}
