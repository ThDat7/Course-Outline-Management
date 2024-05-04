package com.dat.service.impl;

import com.dat.pojo.Course;
import com.dat.repository.CourseRepository;
import com.dat.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl
        extends BaseServiceImpl<Course, Integer>
        implements CourseService {

    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        super(courseRepository);
        this.courseRepository = courseRepository;
    }
}
