package com.dat.repository;

import com.dat.dto.AssignOutlineDto;
import com.dat.pojo.EducationProgram;
import com.dat.pojo.EducationProgramCourse;

import java.util.List;
import java.util.Map;

public interface EducationProgramCourseRepository {
    void addOrUpdate(EducationProgramCourse educationProgramCourse);

    EducationProgramCourse getById(int id);

    EducationProgramCourse getByEpIdAndCourseId(int epId, int courseId);

    EducationProgram getView(int id);
}
