package com.dat.service;

import com.dat.pojo.Course;

import java.util.List;

public interface CourseService extends BaseService<Course, Integer> {
    List<Course> getAll();
}
