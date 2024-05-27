package com.dat.service;

import com.dat.pojo.CourseOutline;
import com.dat.pojo.EducationProgramCourse;

import java.util.List;
import java.util.Map;

public interface EducationProgramCourseService {
    EducationProgramCourse getById(int id);

    void reuseOutline(int epcId, int coId);

    void removeOutline(int epId, int courseId);

    void associateOutline(int epcId, CourseOutline courseOutline,
                          List<String> type, List<String> method, List<String> time,
                          List<String> clos, List<Integer> weightPercent, List<Integer> schoolYears);
}
