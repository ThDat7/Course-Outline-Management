package com.dat.repository.impl;

import com.dat.pojo.Faculty;
import com.dat.pojo.Teacher;
import com.dat.pojo.User;
import com.dat.repository.FacultyRepository;
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
public class FacultyRepositoryImpl
        extends BaseRepositoryImpl<Faculty, Integer>
        implements FacultyRepository {
    public FacultyRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(Faculty faculty) {
        return faculty.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("kw"))
            predicates.add(b.like(root.get("name"), String.format("%%%s%%", params.get("kw"))));

        return predicates;
    }

    @Override
    protected void joinRelationGetById(Root root) {

    }

    @Override
    protected void joinRelationGetAll(Root root) {

    }

    @Override
    public List<Faculty> getAll() {
        Session s = factory.getObject().openSession();
        return s.createQuery("SELECT f FROM Faculty f", Faculty.class).list();
    }
}
