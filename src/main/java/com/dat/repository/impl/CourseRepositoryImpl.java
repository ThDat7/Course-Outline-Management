package com.dat.repository.impl;

import com.dat.pojo.Course;
import com.dat.pojo.User;
import com.dat.repository.CourseRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Parameter;
import javax.persistence.criteria.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class CourseRepositoryImpl
        extends BaseRepositoryImpl<Course, Integer>
        implements CourseRepository {

    public CourseRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(Course course) {
        return course.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("kw")) {
            predicates.add(b.like(root.get("name"), "%" + params.get("kw") + "%"));
        }

        if (params.containsKey("credits")) {
            predicates.add(b.equal(root.get("credits"), Integer.parseInt(params.get("credits"))));
        }

        if (params.containsKey("faculty"))
            predicates.add(b.equal(root
                            .joinSet("educationProgramCourses")
                            .join("educationProgram")
                            .join("major")
                            .join("faculty")
                            .get("id"),
                    Integer.parseInt(params.get("faculty"))));

        return predicates;
    }

    @Override
    public List<Course> getAll() {
        Session s = factory.getObject().openSession();
        return s.createQuery("SELECT c FROM Course c", Course.class).list();
    }

    @Override
    public List<Course> getCourseNotCreatedAssign(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery q = b.createQuery(Course.class);
        Root root = q.from(Course.class);
        q.select(root).distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        Predicate filterNotAssign = getCourseNotCreatedAssignPredicate(params, b, root);
        if (filterNotAssign != null)
            predicates.add(filterNotAssign);

        if (params != null && params.size() > 0)
            predicates.addAll(filterByParams(params, b, root));

        q.where(predicates.toArray(Predicate[]::new));

        q.orderBy(b.asc(root.get("id")));

        Query query = s.createQuery(q);

        filterPage(params, query);

        return query.getResultList();
    }

    private Predicate getCourseNotCreatedAssignPredicate(Map<String, String> params, CriteriaBuilder b, Root root) {
//        SELECT COUNT(c) FROM Course c WHERE c.id NOT IN " +
//        "(SELECT c.id FROM Course c JOIN c.assignOutlines ao " +
//                "JOIN ao.courseOutline co JOIN co.courseOutlineDetails cod " +
//                "WHERE cod.id.schoolYear = :year
        int year = Year.now().getValue();
        if (params != null && params.containsKey("year"))
            year = Integer.parseInt(params.get("year"));

        Subquery<Long> subquery = b.createQuery().subquery(Long.class);
        Root<Course> subRoot = subquery.from(Course.class);

        subquery.select(subRoot.get("id"));
        Predicate p = b.equal(subRoot
                .joinList("assignOutlines")
                .join("courseOutline")
                .joinList("courseOutlineDetails")
                .get("id").get("schoolYear"), year);
        subquery.where(p);

        return b.not(root.get("id").in(subquery));
    }

    @Override
    public Long countCourseNotCreatedAssign(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();

        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery q = b.createQuery(Long.class);
        Root root = q.from(Course.class);

        List<Predicate> predicates = new ArrayList<>();
        Predicate filterNotAssign = getCourseNotCreatedAssignPredicate(params, b, root);

        if (filterNotAssign != null)
            predicates.add(filterNotAssign);

        if (params != null && params.size() > 0)
            predicates.addAll(filterByParams(params, b, root));

        q.select(b.countDistinct(root));
        q.where(predicates.toArray(Predicate[]::new));

        javax.persistence.Query query = s.createQuery(q);
        return Long.parseLong(query.getSingleResult().toString());
    }
}
