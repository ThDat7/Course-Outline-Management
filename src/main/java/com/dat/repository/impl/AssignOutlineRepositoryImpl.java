package com.dat.repository.impl;

import com.dat.pojo.AssignOutline;
import com.dat.pojo.User;
import com.dat.repository.AssignOutlineRepository;
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
public class AssignOutlineRepositoryImpl
        extends BaseRepositoryImpl<AssignOutline, Integer>
        implements AssignOutlineRepository {

    public AssignOutlineRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(AssignOutline assignOutline) {
        return assignOutline.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root<User> root) {
        return null;
    }
}
