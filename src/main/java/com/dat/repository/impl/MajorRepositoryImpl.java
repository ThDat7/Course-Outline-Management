package com.dat.repository.impl;

import com.dat.pojo.Major;
import com.dat.pojo.Teacher;
import com.dat.pojo.User;
import com.dat.repository.MajorRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MajorRepositoryImpl
        extends BaseRepositoryImpl<Major, Integer>
        implements MajorRepository {
    @Autowired
    protected LocalSessionFactoryBean factory;

    public MajorRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(Major major) {
        return major.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("kw"))
            predicates.add(b.like(root.get("name"), String.format("%%%s%%", params.get("kw"))));

        if (params.containsKey("faculty")) {
            predicates.add(b.equal(root.get("faculty").get("id"), Integer.parseInt(params.get("faculty"))));
        }

        return predicates;
    }

    public List<Major> getAll() {
        Session s = factory.getObject().openSession();
        return s.createQuery("SELECT m FROM Major m", Major.class).list();
    }

}
