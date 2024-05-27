package com.dat.repository.impl;

import com.dat.pojo.*;
import com.dat.repository.CourseOutlineRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.time.Year;
import java.util.ArrayList;
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
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("kw"))
            predicates.add(b.like(root.get("assignOutline").get("course").get("name"), "%" + params.get("kw") + "%"));

        if (params.containsKey("status"))
            predicates.add(b.equal(root.get("status"), OutlineStatus.valueOf(params.get("status"))));

        if (params.containsKey("course"))
            predicates.add(b.equal(root.get("assignOutline").get("course").get("id"), Integer.parseInt(params.get("course"))));

        if (params.containsKey("major"))
            predicates.add(b.equal(root.join("assignOutline")
                    .join("course")
                    .joinSet("educationPrograms")
                    .join("major"), Integer.parseInt(params.get("major"))));

        if (params.containsKey("year"))
            predicates.add(b.equal(root
                    .joinSet("courseOutlineDetails")
                    .get("id").get("schoolYear"), Integer.parseInt(params.get("year"))));

        return predicates;
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
