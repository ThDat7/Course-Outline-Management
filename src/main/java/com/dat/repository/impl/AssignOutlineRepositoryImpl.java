package com.dat.repository.impl;

import com.dat.pojo.AssignOutline;
import com.dat.pojo.OutlineStatus;
import com.dat.pojo.User;
import com.dat.repository.AssignOutlineRepository;
import org.hibernate.Session;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("kw")) {
            predicates.add(b.like(root.get("course").get("name"), "%" + params.get("kw") + "%"));
        }

        String status = params.get("status");
        if (status != null && !status.isEmpty()) {
            if (status.equals("NOT_CREATED"))
                predicates.add(b.isNull(root.get("courseOutline")));
            else
                predicates.add(b.equal(root.get("courseOutline").get("status"), OutlineStatus.valueOf(status)));
        }

        String teacherId = params.get("teacher");
        if (teacherId != null && !teacherId.isEmpty()) {
            predicates.add(b.equal(root.get("teacher").get("id"), Integer.parseInt(teacherId)));
        }

        String courseId = params.get("course");
        if (courseId != null && !courseId.isEmpty()) {
            predicates.add(b.equal(root.get("course").get("id"), Integer.parseInt(courseId)));
        }

        return predicates;
    }

    @Override
    public List<AssignOutline> findByTeacherId(int teacherId) {
        Session s = factory.getObject().openSession();
        return s.createQuery("SELECT a FROM AssignOutline a " +
                        "WHERE a.teacher.id = :teacherId", AssignOutline.class)
                .setParameter("teacherId", teacherId)
                .getResultList();
    }
}
