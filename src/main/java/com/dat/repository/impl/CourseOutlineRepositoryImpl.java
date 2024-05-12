package com.dat.repository.impl;

import com.dat.pojo.CourseOutline;
import com.dat.pojo.User;
import com.dat.repository.CourseOutlineRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class CourseOutlineRepositoryImpl
        extends BaseRepositoryImpl<CourseOutline, Integer>
        implements CourseOutlineRepository {

    public CourseOutlineRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(CourseOutline courseOutline) {
        return courseOutline.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root<User> root) {
        return null;
    }

    @Override
    public boolean addOrUpdate(CourseOutline courseOutline) {
        Session s = factory.getObject().getCurrentSession();
        try {
            if (courseOutline.getId() == null)
                s.save(courseOutline);
            else s.merge(courseOutline);

            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }
}
