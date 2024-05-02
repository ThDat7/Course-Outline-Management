package com.dat.repository.impl;

import com.dat.pojo.User;
import com.dat.repository.UserRepository;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment env;

    @Override
    public List<User> getUsers(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery q = b.createQuery(User.class);
        Root root = q.from(User.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty())
                predicates.add(b.like(root.get("username"), String.format("%%%s%%", kw)));
        }

        q.orderBy(b.asc(root.get("id")));

        Query query = s.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE"));

                query.setMaxResults(pageSize);
                query.setFirstResult((p - 1) * pageSize);
            }
        }

        return query.getResultList();
    }

    @Override
    public Long countUser() {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM User");

        return Long.parseLong(q.getSingleResult().toString());
    }

    @Override
    public boolean addOrUpdateUser(User u) {
        Session s = factory.getObject().getCurrentSession();
        try {
            if (u.getId() == null)
                s.save(u);
            else s.update(u);

            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }
}
