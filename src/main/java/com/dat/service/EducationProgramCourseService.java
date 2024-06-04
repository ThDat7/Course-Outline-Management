package com.dat.service;

import com.dat.pojo.CourseOutline;
import com.dat.pojo.EducationProgram;
import com.dat.pojo.EducationProgramCourse;

import java.util.List;
import java.util.Map;

public interface EducationProgramCourseService {
    EducationProgramCourse getById(int id);

    void reuseOutline(int epcId, int coId);

    void removeOutline(int epId, int courseId);

    void associateOutline(int epcId, CourseOutline courseOutline);

    EducationProgram getView(int id);
}
