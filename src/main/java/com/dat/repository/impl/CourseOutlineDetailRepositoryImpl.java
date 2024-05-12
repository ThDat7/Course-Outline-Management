package com.dat.repository.impl;

import com.dat.pojo.CourseOutlineDetail;
import com.dat.repository.CourseOutlineDetailRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class CourseOutlineDetailRepositoryImpl implements CourseOutlineDetailRepository {
    @Autowired
    protected LocalSessionFactoryBean factory;

    @Override
    public void saveOrUpdate(CourseOutlineDetail detail) {
        Session s = factory.getObject().getCurrentSession();
        s.saveOrUpdate(detail);
    }
}
