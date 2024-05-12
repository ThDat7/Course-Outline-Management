package com.dat.repository.impl;

import com.dat.pojo.EducationProgram;
import com.dat.repository.EducationProgramRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EducationProgramRepositoryImpl implements EducationProgramRepository {
    @Autowired
    protected LocalSessionFactoryBean factory;

    @Override
    public void saveOrUpdate(EducationProgram educationProgram) {
        Session s = factory.getObject().getCurrentSession();
        s.saveOrUpdate(educationProgram);
    }
}
