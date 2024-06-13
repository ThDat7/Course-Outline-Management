package com.dat.service.impl;

import com.dat.pojo.CourseOutline;
import com.dat.pojo.EducationProgram;
import com.dat.pojo.EducationProgramCourse;
import com.dat.pojo.OutlineStatus;
import com.dat.repository.CourseOutlineRepository;
import com.dat.repository.EducationProgramCourseRepository;
import com.dat.service.CourseOutlineService;
import com.dat.service.EducationProgramCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Map;

@Service
public class EducationProgramCourseServiceImpl implements EducationProgramCourseService {
    @Autowired
    private EducationProgramCourseRepository educationProgramCourseRepository;

    @Autowired
    private CourseOutlineRepository courseOutlineRepository;

    @Autowired
    private CourseOutlineService courseOutlineService;

    public EducationProgramCourse getById(int id) {
        return educationProgramCourseRepository.getById(id);
    }

    @Override
    public void reuseOutline(int epcId, int coId) {
        EducationProgramCourse epc = educationProgramCourseRepository.getById(epcId);
        CourseOutline co = new CourseOutline();
        co.setId(coId);
        epc.setCourseOutline(co);
        educationProgramCourseRepository.addOrUpdate(epc);
    }

    @Override
    public void removeOutline(int epId, int courseId) {
        EducationProgramCourse epc = educationProgramCourseRepository.getByEpIdAndCourseId(epId, courseId);
        epc.setCourseOutline(null);
        educationProgramCourseRepository.addOrUpdate(epc);
    }

    @Override
    public EducationProgram getView(int id) {
        return educationProgramCourseRepository.getView(id);
    }
}
