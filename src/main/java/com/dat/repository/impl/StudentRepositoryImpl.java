package com.dat.repository.impl;

import com.dat.pojo.Student;
import com.dat.repository.StudentRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentRepositoryImpl extends BaseRepositoryImpl<Student, Integer> implements StudentRepository {
    @Autowired
    protected LocalSessionFactoryBean factory;

    public StudentRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    public void add(Student student) {
        Session s = factory.getObject().getCurrentSession();
        s.save(student);
    }

    @Override
    public Student getByUserId(Integer userId) {
        Session s = factory.getObject().getCurrentSession();
        return s.createQuery("SELECT s FROM Student s where s.user.id = :userId", Student.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public void update(Student student) {
        Session s = factory.getObject().getCurrentSession();
        s.update(student);
    }

    @Override
    protected Integer getId(Student student) {
        return student.getId();
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
    public void delete(Integer id) {
        Session s = factory.getObject().getCurrentSession();
        Student student = s.get(Student.class, id);
        s.delete(student);
    }

    @Override
    protected void joinRelationGetById(Root root) {

    }

    @Override
    protected void joinRelationGetAll(Root root) {

    }
}
