package com.dat.repository.impl;

import com.dat.pojo.User;
import com.dat.pojo.UserRole;
import com.dat.pojo.UserStatus;
import com.dat.repository.UserRepository;
import org.hibernate.Session;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class UserRepositoryImpl
        extends BaseRepositoryImpl<User, Integer>
        implements UserRepository {

    public UserRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(User user) {
        return user.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root) {
        List<Predicate> predicates = new ArrayList<>();

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            Expression<String> fullNameExpression = b.concat(b.concat(root.get("lastName"), " "), root.get("firstName"));
            predicates.add(b.like(fullNameExpression, String.format("%%%s%%", kw)));
        }

        String userStatusString = params.get("userStatus");
        if (userStatusString != null && !userStatusString.isEmpty()) {
            UserStatus userStatus = UserStatus.valueOf(userStatusString);
            predicates.add(b.equal(root.get("status"), userStatus));
        }

        if (params.containsKey("role")) {
            predicates.add(b.equal(root.get("role"), UserRole.valueOf(params.get("role"))));
        }

        if (params.containsKey("status")) {
            predicates.add(b.equal(root.get("status"), UserStatus.valueOf(params.get("status"))));
        }

        return predicates;
    }

    @Override
    protected void joinRelationGetById(Root root) {

    }

    @Override
    protected void joinRelationGetAll(Root root) {

    }

    @Override
    public void updateStatus(Integer id, UserStatus status) {
        Session s = factory.getObject().getCurrentSession();
        User u = s.get(User.class, id);
        if (u == null)
            return;

        u.setStatus(status);
        s.update(u);
    }

    @Override
    public User findByUsername(String username) {
        Session s = factory.getObject().getCurrentSession();
        return s.createQuery("SELECT u FROM User u where u.username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    public User findByIdAndStatus(Integer id, UserStatus status) {
        Session s = factory.getObject().getCurrentSession();
        return s.createQuery("SELECT u FROM User u where u.id = :id and u.status = :status", User.class)
                .setParameter("id", id)
                .setParameter("status", status)
                .uniqueResult();
    }
}
