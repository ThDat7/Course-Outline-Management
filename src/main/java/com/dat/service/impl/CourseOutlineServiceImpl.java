package com.dat.service.impl;

import com.dat.pojo.*;
import com.dat.repository.CourseAssessmentRepository;
import com.dat.repository.CourseOutlineRepository;
import com.dat.service.CourseOutlineService;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
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
    public boolean addOrUpdate(CourseOutline courseOutline) {
        CourseOutline oldCourseOutline = null;
        if (courseOutline.getId() != null) {
            oldCourseOutline = courseOutlineRepository.getById(courseOutline.getId());
            courseOutline.setYearPublished(oldCourseOutline.getYearPublished());
        } else courseOutline.setYearPublished(Year.now().getValue());

        courseOutline.setCourseAssessments(
                updateCourseAssessments(courseOutline.getCourseAssessments(),
                        oldCourseOutline));

        return courseOutlineRepository.addOrUpdate(courseOutline);
    }

    public List<CourseOutline> search(Map<String, String> params) {
        params.replace("status", OutlineStatus.PUBLISHED.name());
        return courseOutlineRepository.getAll(params);
    }

    @Override
    public List<CourseOutline> getByCurrentTeacher(Map<String, String> params) {
        int currentTeacherId = 1;

        return courseOutlineRepository.getByTeacherId(currentTeacherId, params);
    }

    @Override
    public long countByCurrentTeacher(Map<String, String> params) {
        int currentTeacherId = 1;
        return courseOutlineRepository.countByTeacherId(currentTeacherId, params);
    }

    @Override
    public boolean update(int id, CourseOutline courseOutline) {
        if (courseOutline.getStatus() == OutlineStatus.PUBLISHED)
            throw new RuntimeException("Cannot update a published course outline");

        courseOutline.setId(id);
        CourseOutline oldCourseOutline = courseOutlineRepository.getById(id);
        courseOutline.setYearPublished(oldCourseOutline.getYearPublished());
        courseOutline.setTeacher(oldCourseOutline.getTeacher());
        courseOutline.setCourse(oldCourseOutline.getCourse());
        courseOutline.setDeadlineDate(oldCourseOutline.getDeadlineDate());

        courseOutline.setCourseAssessments(
                updateCourseAssessments(courseOutline.getCourseAssessments(),
                        oldCourseOutline));

        return courseOutlineRepository.addOrUpdate(courseOutline);
    }

    private List<CourseAssessment> updateCourseAssessments(List<CourseAssessment> newCA,
                                                           CourseOutline oldCo) {
        List<CourseAssessment> oldCA = oldCo.getCourseAssessments();
        int oldSize = oldCA.size();
        int newSize = newCA.size();

        for (int i = 0; i < oldSize - newSize; i++)
            oldCA.remove(0);

        for (int i = 0; i < newSize - oldSize; i++) {
            CourseAssessment ca = new CourseAssessment();
            ca.setAssessmentMethods(new ArrayList<>());
            ca.setCourseOutline(oldCo);
            oldCA.add(ca);
        }

        for (int i = 0; i < newSize; i++) {
            CourseAssessment newCourseAssessment = newCA.get(i);
            CourseAssessment oldCourseAssessment = oldCA.get(i);

            oldCourseAssessment.setType(newCourseAssessment.getType());
            oldCourseAssessment.setAssessmentMethods(
                    updateAssessmentMethods(newCourseAssessment.getAssessmentMethods(),
                            oldCourseAssessment));
        }

        return oldCA;

    }

    private List<AssessmentMethod> updateAssessmentMethods(List<AssessmentMethod> newAM, CourseAssessment oldAssessment) {
        List<AssessmentMethod> oldAM = oldAssessment.getAssessmentMethods();

        int oldSize = oldAM.size();
        int newSize = newAM.size();

        for (int i = 0; i < oldSize - newSize; i++)
            oldAM.remove(0);

        for (int i = 0; i < newSize - oldSize; i++) {
            AssessmentMethod am = new AssessmentMethod();
            am.setCourseAssessment(oldAssessment);
            oldAM.add(am);
        }

        for (int i = 0; i < newSize; i++) {
            AssessmentMethod newAssessmentMethod = newAM.get(i);
            AssessmentMethod oldAssessmentMethod = oldAM.get(i);

            oldAssessmentMethod.setMethod(newAssessmentMethod.getMethod());
            oldAssessmentMethod.setTime(newAssessmentMethod.getTime());
            oldAssessmentMethod.setClos(newAssessmentMethod.getClos());
            oldAssessmentMethod.setWeightPercent(newAssessmentMethod.getWeightPercent());
        }

        return oldAM;
    }
}
