package com.dat.repository;

import com.dat.pojo.Course;

import java.util.List;

public interface CourseRepository extends BaseRepository<Course, Integer> {
    List<Course> getAll();
}
