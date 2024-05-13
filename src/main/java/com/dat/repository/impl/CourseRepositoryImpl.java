package com.dat.repository.impl;

import com.dat.pojo.Course;
import com.dat.pojo.User;
import com.dat.repository.CourseRepository;
import org.hibernate.Session;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class CourseRepositoryImpl
        extends BaseRepositoryImpl<Course, Integer>
        implements CourseRepository {

    public CourseRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(Course course) {
        return course.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("kw")) {
            predicates.add(b.like(root.get("name"), "%" + params.get("kw") + "%"));
        }

        if (params.containsKey("credits")) {
            predicates.add(b.equal(root.get("credits"), Integer.parseInt(params.get("credits"))));
        }

        return predicates;
    }

    @Override
    public List<Course> getAll() {
        Session s = factory.getObject().openSession();
        return s.createQuery("SELECT c FROM Course c", Course.class).list();
    }
}
