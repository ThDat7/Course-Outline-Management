package com.dat.repository.impl;

import com.dat.pojo.*;
import com.dat.repository.CourseOutlineRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class CourseOutlineRepositoryImpl
        extends BaseRepositoryImpl<CourseOutline, Integer>
        implements CourseOutlineRepository {

    public CourseOutlineRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(CourseOutline courseOutline) {
        return courseOutline.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("kw"))
            predicates.add(b.like(root.get("course").get("name"), "%" + params.get("kw") + "%"));

        if (params.containsKey("status"))
            predicates.add(b.equal(root.get("status"), OutlineStatus.valueOf(params.get("status"))));

        if (params.containsKey("course"))
            predicates.add(b.equal(root.get("course").get("id"), Integer.parseInt(params.get("course"))));

        if (params.containsKey("major"))
            predicates.add(b.equal(root
                    .joinSet("educationProgramCourses")
                    .join("educationProgram")
                    .join("major").get("id"), Integer.parseInt(params.get("major"))));

        if (params.containsKey("year"))
            predicates.add(b.equal(root.get("yearPublished"), Integer.parseInt(params.get("year"))));

        return predicates;
    }

    @Override
    public boolean addOrUpdate(CourseOutline courseOutline) {
        Session s = factory.getObject().getCurrentSession();
        try {
            if (courseOutline.getId() == null)
                s.save(courseOutline);
            else s.merge(courseOutline);

            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void joinRelationGetById(Root root) {
        root.fetch("teacher", JoinType.LEFT).fetch("user", JoinType.LEFT);
        root.fetch("course", JoinType.LEFT);
        root.fetch("courseAssessments", JoinType.LEFT);
    }

    @Override
    protected void joinRelationGetAll(Root root) {
        root.fetch("teacher", JoinType.LEFT).fetch("user", JoinType.LEFT);
    }

    @Override
    public List<CourseOutline> getByTeacherId(int teacherId, Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<CourseOutline> q = b.createQuery(CourseOutline.class);
        Root<CourseOutline> root = q.from(CourseOutline.class);

        joinRelationGetById(root);
        q.select(root);
        Predicate pStatus = b.equal(root.get("status"), OutlineStatus.DOING);
        Predicate pTeacher = b.equal(root.get("teacher").get("id"), teacherId);
        q.where(b.and(pStatus, pTeacher));

        Query query = s.createQuery(q);
        filterPage(params, query);

        return query.getResultList();
    }

    @Override
    public long countByTeacherId(int teacherId, Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();

        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery q = b.createQuery(CourseOutline.class);
        Root root = q.from(CourseOutline.class);
        q.select(b.count(root));

        Predicate pStatus = b.equal(root.get("status"), OutlineStatus.DOING);
        Predicate pTeacher = b.equal(root.get("teacher").get("id"), teacherId);
        q.where(b.and(pStatus, pTeacher));

        Query query = s.createQuery(q);
        return Long.parseLong(query.getSingleResult().toString());
    }
}
