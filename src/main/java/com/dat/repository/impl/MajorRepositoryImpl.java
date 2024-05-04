package com.dat.repository.impl;

import com.dat.pojo.Major;
import com.dat.pojo.User;
import com.dat.repository.MajorRepository;
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
public class MajorRepositoryImpl
        extends BaseRepositoryImpl<Major, Integer>
        implements MajorRepository {
    public MajorRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(Major major) {
        return major.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root<User> root) {
        return null;
    }
}
