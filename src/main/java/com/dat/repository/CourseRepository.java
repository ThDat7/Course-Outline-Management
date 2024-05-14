package com.dat.repository;

import com.dat.pojo.Course;

import java.util.List;
import java.util.Map;

public interface CourseRepository extends BaseRepository<Course, Integer> {
    List<Course> getAll();

    Long countCourseNotCreatedAssign(Map<String, String> params);

    List<Course> getCourseNotCreatedAssign(Map<String, String> params);
}
