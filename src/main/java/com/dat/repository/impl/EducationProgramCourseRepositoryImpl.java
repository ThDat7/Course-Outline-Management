package com.dat.repository.impl;

import com.dat.dto.AssignOutlineDto;
import com.dat.pojo.*;
import com.dat.repository.EducationProgramCourseRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class EducationProgramCourseRepositoryImpl implements EducationProgramCourseRepository {
    @Autowired
    protected LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdate(EducationProgramCourse educationProgramCourse) {
        Session s = factory.getObject().getCurrentSession();
        s.saveOrUpdate(educationProgramCourse);
    }

    @Override
    public EducationProgramCourse getById(int id) {
        Session s = factory.getObject().getCurrentSession();
        return s.createQuery("SELECT epc FROM EducationProgramCourse epc where epc.id = :id", EducationProgramCourse.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public EducationProgramCourse getByEpIdAndCourseId(int epId, int courseId) {
        Session s = factory.getObject().getCurrentSession();
        return s.createQuery("SELECT epc FROM EducationProgramCourse epc " +
                        "WHERE epc.educationProgram.id = :epId ANd epc.course.id = :courseId", EducationProgramCourse.class)
                .setParameter("epId", epId)
                .setParameter("courseId", courseId)
                .getSingleResult();
    }
}
