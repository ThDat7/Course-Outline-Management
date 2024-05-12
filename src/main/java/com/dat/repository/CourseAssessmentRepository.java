package com.dat.repository;

import com.dat.pojo.CourseAssessment;

public interface CourseAssessmentRepository {
    void saveOrUpdate(CourseAssessment courseAssessment);
}
