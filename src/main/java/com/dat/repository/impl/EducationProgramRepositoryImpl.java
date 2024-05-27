package com.dat.repository.impl;

import com.dat.dto.AssignOutlineDto;
import com.dat.dto.DataWithCounterDto;
import com.dat.pojo.*;
import com.dat.repository.EducationProgramRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class EducationProgramRepositoryImpl
        extends BaseRepositoryImpl<EducationProgram, Integer>
        implements EducationProgramRepository {
    public EducationProgramRepositoryImpl(LocalSessionFactoryBean factory, Environment env) {
        super(factory, env);
    }

    @Override
    protected Integer getId(EducationProgram educationProgram) {
        return educationProgram.getId();
    }

    @Override
    protected List<Predicate> filterByParams(Map<String, String> params, CriteriaBuilder b, Root root) {
        return null;
    }


    @Override
    public DataWithCounterDto<AssignOutlineDto> getReuse(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery q = b.createQuery(AssignOutlineDto.class);
        Root educationProgramRoot = q.from(EducationProgram.class);

        SetJoin<EducationProgram, EducationProgramCourse> epcJoin = educationProgramRoot.joinSet("educationProgramCourses");
        Join<EducationProgram, Major> majorJoin = educationProgramRoot.join("major");
        Join<EducationProgramCourse, Course> courseJoin = epcJoin.join("course");
        SetJoin<Course, CourseOutline> courseOutlineJoin = courseJoin.joinSet("courseOutlines");

        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(getAssignOutlinePredicates(params, b, educationProgramRoot, courseJoin));

        int year = Year.now().getValue();
        if (params.containsKey("year"))
            year = Integer.parseInt(params.get("year"));
        predicates.add(b.equal(educationProgramRoot.get("schoolYear"), year));
        predicates.add(b.isNull(epcJoin.get("courseOutline")));

        predicates.add(b.and(b.greaterThan(courseOutlineJoin.get("yearPublished"), year - 2),
                b.lessThanOrEqualTo(courseOutlineJoin.get("yearPublished"), year)));
        q.where(predicates.toArray(new Predicate[0]));

        long total = (long) s.createQuery(q.select(b.count(educationProgramRoot))).getSingleResult();
        q.multiselect(epcJoin.get("id"), courseOutlineJoin.get("id"), courseJoin.get("name"), majorJoin.get("name"), courseOutlineJoin.get("yearPublished"));

        Query query = s.createQuery(q);

        if (params.containsKey("page"))
            filterPage(params, query);

        List<AssignOutlineDto> data = query.getResultList();

        return new DataWithCounterDto<>(data, total);
    }

    public DataWithCounterDto<AssignOutlineDto> getNeedCreate(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery q = b.createQuery(AssignOutlineDto.class);
        Root educationProgramRoot = q.from(EducationProgram.class);

        SetJoin<EducationProgram, EducationProgramCourse> epcJoin = educationProgramRoot.joinSet("educationProgramCourses");
        Join<EducationProgram, Major> majorJoin = educationProgramRoot.join("major");
        Join<EducationProgramCourse, Course> courseJoin = epcJoin.join("course");

        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(getAssignOutlinePredicates(params, b, educationProgramRoot, courseJoin));

        int year = Year.now().getValue();
        if (params.containsKey("year"))
            year = Integer.parseInt(params.get("year"));
        predicates.add(b.equal(educationProgramRoot.get("schoolYear"), year));
        predicates.add(b.isNull(epcJoin.get("courseOutline")));

        Subquery subquery = q.subquery(CourseOutline.class);
        Root subRoot = subquery.from(CourseOutline.class);
        subquery.select(subRoot);
        subquery.where(
                b.equal(subRoot.get("course"), courseJoin),
                b.greaterThan(subRoot.get("yearPublished"), year - 2)
        );

        predicates.add(
                b.not(b.exists(subquery))
        );
        q.where(predicates.toArray(new Predicate[0]));

        long total = (long) s.createQuery(q.select(b.count(epcJoin))).getSingleResult();
        q.multiselect(epcJoin.get("id"), courseJoin.get("name"), majorJoin.get("name"));

        Query query = s.createQuery(q);

        if (params.containsKey("page"))
            filterPage(params, query);
        List<AssignOutlineDto> data = query.getResultList();

        return new DataWithCounterDto<>(data, total);
    }

    private List<Predicate> getAssignOutlinePredicates(Map<String, String> params, CriteriaBuilder b,
                                                       Root root, Join courseJoin) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("major"))
            predicates.add(b.equal(root.get("major").get("id"), Integer.parseInt(params.get("major"))));

        if (params.containsKey("course"))
            predicates.add(b.equal(courseJoin.get("id"), Integer.parseInt(params.get("course"))));

        if (params.containsKey("kw"))
            predicates.add(b.or(b.like(root.get("major").get("name"), "%" + params.get("kw") + "%"),
                    b.like(courseJoin.get("name"), "%" + params.get("kw") + "%")));

        return predicates;
    }


    @Override
    public void reuseAll(int year) {
        Session s = factory.getObject().getCurrentSession();
//        Query q = s.createQuery("UPDATE EducationProgramCourse epc " +
//                        "SET epc.courseOutline.id = " +
//                        "(SELECT MAX(co.id) FROM CourseOutline co " +
//                        "WHERE co.course = epc.course " +
//                        "AND co.yearPublished > :year - 2 " +
//                        "AND co.yearPublished <= :year " +
//                        "AND co.yearPublished = " +
//                        "(SELECT MAX(coMaxYear.yearPublished) " +
//                        "FROM CourseOutline coMaxYear " +
//                        "WHERE coMaxYear.course = epc.course)) " +
//                        "WHERE epc.educationProgram.schoolYear = :year")
//                .setParameter("year", year);

        String selectQuery = "SELECT epc.id, " +
                "(SELECT co.id FROM CourseOutline co " +
                "WHERE co.course = epc.course " +
                "AND co.yearPublished = " +
                "(SELECT MAX(coMaxYear.yearPublished) " +
                "FROM CourseOutline coMaxYear " +
                "WHERE coMaxYear.course = epc.course " +
                "AND co.yearPublished > :year - 2 " +
                "AND co.yearPublished <= :year)) as courseOutlineId " +
                "FROM EducationProgramCourse epc " +
                "WHERE epc.educationProgram.schoolYear = :year";

        List<Object[]> results = s.createQuery(selectQuery)
                .setParameter("year", year)
                .list();

// Step 2: Update the EducationProgramCourse table based on the retrieved data
        for (Object[] result : results) {
            Integer epcId = (Integer) result[0];
            Integer courseOutlineId = (Integer) result[1];

            if (courseOutlineId != null) {
                String updateQuery = "UPDATE EducationProgramCourse epc " +
                        "SET epc.courseOutline.id = :courseOutlineId " +
                        "WHERE epc.id = :epcId";
                s.createQuery(updateQuery)
                        .setParameter("courseOutlineId", courseOutlineId)
                        .setParameter("epcId", epcId)
                        .executeUpdate();
            }
        }
    }
}
