package com.dat.repository.impl;

import com.dat.pojo.Student;
import com.dat.repository.StudentRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {
    @Autowired
    protected LocalSessionFactoryBean factory;

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
}
