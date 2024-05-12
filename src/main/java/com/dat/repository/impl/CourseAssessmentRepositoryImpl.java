package com.dat.repository.impl;

import com.dat.pojo.CourseAssessment;
import com.dat.repository.CourseAssessmentRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CourseAssessmentRepositoryImpl
        implements CourseAssessmentRepository {

    @Autowired
    protected LocalSessionFactoryBean factory;

    @Override
    public void saveOrUpdate(CourseAssessment courseAssessment) {
        Session s = factory.getObject().getCurrentSession();
        s.saveOrUpdate(courseAssessment);
    }
}
