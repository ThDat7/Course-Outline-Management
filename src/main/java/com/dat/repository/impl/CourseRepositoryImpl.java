package com.dat.repository.impl;

import com.dat.pojo.Course;
import com.dat.pojo.User;
import com.dat.repository.CourseRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Parameter;
import javax.persistence.criteria.*;
import java.time.Year;
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

        if (params.containsKey("faculty"))
            predicates.add(b.equal(root
                            .joinSet("educationProgramCourses")
                            .join("educationProgram")
                            .join("major")
                            .join("faculty")
                            .get("id"),
                    Integer.parseInt(params.get("faculty"))));

        return predicates;
    }

    @Override
    protected void joinRelationGetById(Root root) {
        root.fetch("educationProgramCourses", JoinType.LEFT)
                .fetch("educationProgram", JoinType.LEFT)
                .fetch("major", JoinType.LEFT);
    }

    @Override
    protected void joinRelationGetAll(Root root) {
        root.fetch("educationProgramCourses", JoinType.LEFT)
                .fetch("educationProgram", JoinType.LEFT)
                .fetch("major", JoinType.LEFT);
    }

    @Override
    public List<Course> getAll() {
        Session s = factory.getObject().openSession();
        return s.createQuery("SELECT c FROM Course c", Course.class).list();
    }
}
