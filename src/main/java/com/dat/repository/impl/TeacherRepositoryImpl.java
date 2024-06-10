package com.dat.repository.impl;

import com.dat.pojo.Course;
import com.dat.pojo.Teacher;
import com.dat.pojo.User;
import com.dat.pojo.UserStatus;
import com.dat.repository.TeacherRepository;
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
public class TeacherRepositoryImpl
        extends BaseRepositoryImpl<Teacher, Integer>
        implements TeacherRepository {

    public TeacherRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(Teacher teacher) {
        return teacher.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root) {
        List<Predicate> predicates = new ArrayList<>();
        if (params.containsKey("major"))
            predicates.add(b.equal(root.get("major").get("id"), params.get("major")));

        if (params.containsKey("status"))
            predicates.add(b.equal(root.get("user").get("status"), params.get("status")));

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            Expression<String> fullNameExpression = b.concat(b.concat(root.get("user").get("lastName"), " "), root.get("user").get("firstName"));
            predicates.add(b.like(fullNameExpression, String.format("%%%s%%", kw)));
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
    public List<Teacher> getAll() {
        Session s = factory.getObject().openSession();
        return s.createQuery("SELECT t FROM Teacher t", Teacher.class).list();
    }

    @Override
    public Teacher getByUserId(int userId) {
        Session s = factory.getObject().getCurrentSession();
        return s.createQuery("SELECT t FROM Teacher t WHERE t.user.id = :userId", Teacher.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public Teacher getByUserIdAndUserStatus(Integer id, UserStatus status) {
        Session s = factory.getObject().getCurrentSession();
        return s.createQuery("SELECT t FROM Teacher t WHERE t.user.id = :id " +
                        "AND t.user.status = :status", Teacher.class)
                .setParameter("id", id)
                .setParameter("status", status)
                .getSingleResult();
    }
}
