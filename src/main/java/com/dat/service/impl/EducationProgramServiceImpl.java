package com.dat.service.impl;

import com.dat.dto.AssignOutlineDto;
import com.dat.dto.DataWithCounterDto;
import com.dat.pojo.*;
import com.dat.repository.EducationProgramCourseRepository;
import com.dat.repository.EducationProgramRepository;
import com.dat.service.EducationProgramService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EducationProgramServiceImpl
        extends BaseServiceImpl<EducationProgram, Integer>
        implements EducationProgramService {
    private EducationProgramRepository educationProgramRepository;

    private EducationProgramCourseRepository educationProgramCourseRepository;

    public EducationProgramServiceImpl(EducationProgramRepository educationProgramRepository,
                                       EducationProgramCourseRepository educationProgramCourseRepository) {
        super(educationProgramRepository);
        this.educationProgramRepository = educationProgramRepository;
        this.educationProgramCourseRepository = educationProgramCourseRepository;
    }

    @Override
    public boolean addOrUpdate(EducationProgram educationProgram, List<String> courses) {
        EducationProgram oldEp = null;
        if (educationProgram.getId() != null)
            oldEp = educationProgramRepository.getById(educationProgram.getId());
        else educationProgram.setEducationProgramCourses(new ArrayList<>());

        if (courses == null) courses = new ArrayList<>();

        List<Integer> courseIds = courses.stream().map(e -> Integer.parseInt(e.split("-")[1])).collect(Collectors.toList());

        if (oldEp != null) {
            educationProgram.setEducationProgramCourses(oldEp.getEducationProgramCourses());
            educationProgram.getEducationProgramCourses().removeIf(ep -> !courseIds.contains(ep.getCourse().getId()));
        }

        for (String course : courses) {
            String[] courseStr = course.split("-");
            int semester = Integer.parseInt(courseStr[0]);
            int courseId = Integer.parseInt(courseStr[1]);

            if (educationProgram.getEducationProgramCourses().stream().anyMatch(ep -> ep.getCourse().getId() == courseId))
                continue;

            EducationProgramCourse epc = new EducationProgramCourse();
            epc.setSemester(semester);
            epc.setCourse(new Course(courseId));
            epc.setEducationProgram(educationProgram);

            educationProgram.getEducationProgramCourses().add(epc);
        }

        return addOrUpdate(educationProgram);
    }

    public DataWithCounterDto<AssignOutlineDto> getReuse(Map<String, String> params) {
        if (!params.containsKey("page"))
            params.put("page", "1");
        return educationProgramRepository.getReuse(params);
    }

    @Override
    public DataWithCounterDto<AssignOutlineDto> getNeedCreate(Map<String, String> params) {
        if (!params.containsKey("page"))
            params.put("page", "1");

        return educationProgramRepository.getNeedCreate(params);
    }

    @Override
    public void reuseAll(int year) {
        educationProgramRepository.reuseAll(year);
    }

    @Override
    public int cloneByYear(int year, int byYear) {
        Long countEducationProgram = educationProgramRepository.countByYear(year);
        if (countEducationProgram > 0)
            throw new RuntimeException("Yêu cầu chương trinh đào tạo năm " + year + " rỗng");

        return educationProgramRepository.cloneByYear(year, byYear);
    }

    @Override
    public DataWithCounterDto<EducationProgram> searchApi(Map<String, String> params) {
        return educationProgramRepository.searchApi(params);
    }
}
