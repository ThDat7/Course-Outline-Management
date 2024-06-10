package com.dat.service.impl;

import com.dat.dto.DataWithCounterDto;
import com.dat.pojo.*;
import com.dat.repository.CourseAssessmentRepository;
import com.dat.repository.CourseOutlineRepository;
import com.dat.service.CourseOutlineService;
import com.dat.service.UserService;
import org.springframework.core.env.Environment;
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
    private UserService userService;


    public CourseOutlineServiceImpl(CourseOutlineRepository courseOutlineRepository,
                                    CourseAssessmentRepository courseAssessmentRepository,
                                    UserService userService) {
        super(courseOutlineRepository);
        this.courseOutlineRepository = courseOutlineRepository;
        this.courseAssessmentRepository = courseAssessmentRepository;
        this.userService = userService;
    }

    @Override
    public boolean addOrUpdate(CourseOutline courseOutline) {
        validateCourseAssessmentPercent(courseOutline.getCourseAssessments());

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

    public DataWithCounterDto<CourseOutline> searchApi(Map<String, String> params) {
        return courseOutlineRepository.searchApi(params);
    }

    @Override
    public List<CourseOutline> getByCurrentTeacher(Map<String, String> params) {
        int currentUserId = userService.getCurrentUser().getId();

        return courseOutlineRepository.getByUserIdCompile(currentUserId, params);
    }

    @Override
    public long countByCurrentTeacher(Map<String, String> params) {
        int currentUserId = userService.getCurrentUser().getId();
        return courseOutlineRepository.countByTeacherId(currentUserId, params);
    }

    @Override
    public CourseOutline getView(int id) {
        return courseOutlineRepository.getView(id);
    }

    @Override
    public CourseOutline getByCurrentTeacherAndId(int id) {
        int currentUserId = userService.getCurrentUser().getId();

        return courseOutlineRepository.getByUserIdCompileAndId(currentUserId, id);
    }

    @Override
    public boolean update(int id, CourseOutline courseOutline) {
        int currentUserId = userService.getCurrentUser().getId();
        Boolean isCanCompile = courseOutlineRepository
                .existByUserIdCompileAndStatus(id, currentUserId, OutlineStatus.DOING);
        if (!isCanCompile)
            throw new RuntimeException("You can't compile this course outline or it's published");

        if (courseOutline.getStatus() == OutlineStatus.PUBLISHED)
            throw new RuntimeException("Cannot update a published course outline");

        validateCourseAssessmentPercent(courseOutline.getCourseAssessments());

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

    private void validateCourseAssessmentPercent(List<CourseAssessment> courseAssessments)
    {
        // check sum of all weight percent of course assessments must be 100
        for (CourseAssessment courseAssessment : courseAssessments) {
            List<AssessmentMethod> assessmentMethods = courseAssessment.getAssessmentMethods();
            if (assessmentMethods.stream().anyMatch(am -> am.getWeightPercent() <= 0))
                throw new RuntimeException("Weight percent of assessment methods must be greater than 0");
        }

        double sum = courseAssessments.stream()
                .mapToInt(ca -> ca.getAssessmentMethods().stream()
                        .mapToInt(AssessmentMethod::getWeightPercent)
                        .sum())
                .sum();
        if (sum != 100)
            throw new RuntimeException("Sum of weight percent of course assessments must be 100");
    }
}
