package com.dat.service.impl;

import com.dat.pojo.CourseOutline;
import com.dat.repository.CourseOutlineRepository;
import com.dat.service.CourseOutlineService;
import org.springframework.stereotype.Service;

@Service
public class CourseOutlineServiceImpl
        extends BaseServiceImpl<CourseOutline, Integer>
        implements CourseOutlineService {

    private CourseOutlineRepository courseOutlineRepository;

    public CourseOutlineServiceImpl(CourseOutlineRepository courseOutlineRepository) {
        super(courseOutlineRepository);
        this.courseOutlineRepository = courseOutlineRepository;
    }
}
