package com.dat.service.impl;

import com.dat.pojo.*;
import com.dat.repository.AssignOutlineRepository;
import com.dat.repository.CourseAssessmentRepository;
import com.dat.repository.CourseOutlineRepository;
import com.dat.service.CourseOutlineService;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseOutlineServiceImpl
        extends BaseServiceImpl<CourseOutline, Integer>
        implements CourseOutlineService {

    private CourseOutlineRepository courseOutlineRepository;
    private CourseAssessmentRepository courseAssessmentRepository;
    private AssignOutlineRepository assignOutlineRepository;


    public CourseOutlineServiceImpl(CourseOutlineRepository courseOutlineRepository,
                                    CourseAssessmentRepository courseAssessmentRepository,
                                    AssignOutlineRepository assignOutlineRepository) {
        super(courseOutlineRepository);
        this.courseOutlineRepository = courseOutlineRepository;
        this.courseAssessmentRepository = courseAssessmentRepository;
        this.assignOutlineRepository = assignOutlineRepository;
    }

    @Override
    public boolean addOrUpdate(CourseOutline courseOutline, List<String> types, List<String> methods, List<String> times, List<String> closes, List<Integer> weightPercents, List<Integer> schoolYears) {
        if (types == null)
            return addOrUpdate(courseOutline);

        CourseOutline oldCourseOutline = courseOutlineRepository.getById(courseOutline.getId());

        updateCourse(courseOutline, oldCourseOutline);
        updateCourseAssessments(courseOutline,
                oldCourseOutline,
                types, methods, times, closes, weightPercents);

        updateSchoolYears(courseOutline, oldCourseOutline, schoolYears);

        return addOrUpdate(courseOutline);
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

    private void updateSchoolYears(CourseOutline courseOutline,
                                   CourseOutline oldCourseOutline,
                                   List<Integer> schoolYears) {

        if (oldCourseOutline != null) {
            courseOutline.setCourseOutlineDetails(oldCourseOutline.getCourseOutlineDetails());
            courseOutline.getCourseOutlineDetails().removeIf(detail -> !schoolYears.contains(detail.getId().getSchoolYear()));
        }

        for (int year : schoolYears) {

            if (courseOutline.getCourseOutlineDetails().stream().anyMatch(detail -> detail.getId().getSchoolYear() == year))
                continue;

            CourseOutlineDetail detail = new CourseOutlineDetail();
            detail.setCourseOutline(courseOutline);
            detail.setId(new CourseOutlineDetailId(courseOutline.getId(), year));

            courseOutline.getCourseOutlineDetails().add(detail);
        }
    }

    private void updateCourse(CourseOutline courseOutline,
                              CourseOutline oldCourseOutline) {
        Course newCourse = courseOutline.getAssignOutline().getCourse();
        courseOutline.setAssignOutline(oldCourseOutline.getAssignOutline());
        courseOutline.getAssignOutline().setCourse(newCourse);
        assignOutlineRepository.addOrUpdate(courseOutline.getAssignOutline());
    }

    public CourseOutline getOrCreateByAssignOutlineId(int assignOutlineId) {
        return courseOutlineRepository.getOrCreateByAssignOutlineId(assignOutlineId);
    }
}
