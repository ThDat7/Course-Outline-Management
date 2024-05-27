package com.dat.service.impl;

import com.dat.pojo.*;
import com.dat.repository.CourseAssessmentRepository;
import com.dat.repository.CourseOutlineRepository;
import com.dat.service.CourseOutlineService;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseOutlineServiceImpl
        extends BaseServiceImpl<CourseOutline, Integer>
        implements CourseOutlineService {

    private CourseOutlineRepository courseOutlineRepository;
    private CourseAssessmentRepository courseAssessmentRepository;


    public CourseOutlineServiceImpl(CourseOutlineRepository courseOutlineRepository,
                                    CourseAssessmentRepository courseAssessmentRepository) {
        super(courseOutlineRepository);
        this.courseOutlineRepository = courseOutlineRepository;
        this.courseAssessmentRepository = courseAssessmentRepository;
    }

    @Override
    public boolean addOrUpdate(CourseOutline courseOutline, List<String> types, List<String> methods, List<String> times, List<String> closes, List<Integer> weightPercents, List<Integer> schoolYears) {
        if (types == null || types.size() == 0)
            return addOrUpdate(courseOutline);

        CourseOutline oldCourseOutline = null;
        if (courseOutline.getId() != null)
            oldCourseOutline = courseOutlineRepository.getById(courseOutline.getId());
        else courseOutline.setYearPublished(Year.now().getValue());

        updateCourseAssessments(courseOutline,
                oldCourseOutline,
                types, methods, times, closes, weightPercents);

        return addOrUpdate(courseOutline);
    }

    public List<CourseOutline> search(Map<String, String> params) {
        params.replace("status", OutlineStatus.PUBLISHED.name());
        return courseOutlineRepository.getAll(params);
    }

    @Override
    public boolean update(int id, CourseOutline courseOutline) {
        CourseOutline oldCourseOutline = courseOutlineRepository.getById(id);
        courseOutline.setId(id);
        updateCourseAssessments(courseOutline,
                oldCourseOutline,
                courseOutline.getCourseAssessments().stream()
                        .map(CourseAssessment::getType)
                        .collect(Collectors.toList()),
                courseOutline.getCourseAssessments().stream()
                        .map(CourseAssessment::getMethod)
                        .collect(Collectors.toList()),
                courseOutline.getCourseAssessments().stream()
                        .map(CourseAssessment::getTime)
                        .collect(Collectors.toList()),
                courseOutline.getCourseAssessments().stream()
                        .map(CourseAssessment::getClos)
                        .collect(Collectors.toList()),
                courseOutline.getCourseAssessments().stream()
                        .map(CourseAssessment::getWeightPercent)
                        .collect(Collectors.toList()));

        oldCourseOutline.setContent(courseOutline.getContent());
        oldCourseOutline.setStatus(courseOutline.getStatus());

        return courseOutlineRepository.addOrUpdate(oldCourseOutline);
    }

    private void updateCourseAssessments(CourseOutline courseOutline,
                                         CourseOutline oldCourseOutline,
                                         List<String> types,
                                         List<String> methods,
                                         List<String> times,
                                         List<String> closes,
                                         List<Integer> weightPercents) {
        if (oldCourseOutline != null) {
            courseOutline.setCourseAssessments(oldCourseOutline.getCourseAssessments());
            courseOutline.getCourseAssessments().removeIf(ca -> !types.contains(ca.getType()));
        }

        for (int i = 0; i < types.size(); i++) {
            if (types.get(i) == null || types.get(i).isEmpty())
                continue;
            String type = types.get(i);
            CourseAssessment ca = new CourseAssessment();

            CourseAssessment oldAssessment = courseOutline.getCourseAssessments().stream()
                    .filter(co -> co.getType().equals(type))
                    .findFirst().orElse(null);
            if (oldAssessment != null)
                ca = oldAssessment;
            else courseOutline.getCourseAssessments().add(ca);
// 3 case => new khong co old delete, old khong co new add, old co new update

            ca.setType(type);
            ca.setClos(closes.get(i));
            ca.setMethod(methods.get(i));
            ca.setTime(times.get(i));
            ca.setWeightPercent(weightPercents.get(i));
            ca.setCourseOutline(courseOutline);
            courseAssessmentRepository.saveOrUpdate(ca);
        }
    }


    @Override
    public List<CourseOutline> getReuse(Map<String, String> params) {
        return null;
    }

    @Override
    public List<CourseOutline> getNeedCreate(Map<String, String> params) {
        return null;
    }

    @Override
    public List<CourseOutline> getPending(Map<String, String> params) {
        return null;
    }
}
