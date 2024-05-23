package com.dat.service.impl;

import com.dat.pojo.Course;
import com.dat.pojo.EducationProgram;
import com.dat.pojo.EducationProgramId;
import com.dat.pojo.Major;
import com.dat.repository.EducationProgramRepository;
import com.dat.repository.MajorRepository;
import com.dat.service.MajorService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MajorServiceImpl
        extends BaseServiceImpl<Major, Integer>
        implements MajorService {

    private MajorRepository majorRepository;
    private EducationProgramRepository educationProgramRepository;

    public MajorServiceImpl(MajorRepository majorRepository, EducationProgramRepository educationProgramRepository) {
        super(majorRepository);
        this.majorRepository = majorRepository;
        this.educationProgramRepository = educationProgramRepository;
    }

    public boolean addOrUpdate(Major major, List<String> courses) {
        Set<EducationProgram> educationPrograms = new HashSet<>();
        Major oldMajor = majorRepository.getById(major.getId());
        List<Integer> courseIds = courses.stream().map(e -> Integer.parseInt(e.split("-")[1])).collect(Collectors.toList());

        if (oldMajor != null) {
            major.setEducationPrograms(oldMajor.getEducationPrograms());
            major.getEducationPrograms().removeIf(ep -> !courseIds.contains(ep.getCourse().getId()));
        }

        for (String course : courses) {
            String[] courseStr = course.split("-");
            int semester = Integer.parseInt(courseStr[0]);
            int courseId = Integer.parseInt(courseStr[1]);

            if (major.getEducationPrograms().stream().anyMatch(ep -> ep.getCourse().getId() == courseId))
                continue;

            EducationProgram educationProgram = new EducationProgram();
            educationProgram.setMajor(major);
            educationProgram.setSemester(semester);
            educationProgram.setCourse(new Course(courseId));
            educationProgram.setId(new EducationProgramId(major.getId(), courseId));
            educationProgramRepository.saveOrUpdate(educationProgram);

            major.getEducationPrograms().add(educationProgram);
        }

        return addOrUpdate(major);
    }

    @Override
    public List<Major> searchEducationPrograms(Map<String, String> params) {
        return null;
    }

    public List<Major> getAll() {
        return majorRepository.getAll();
    }
}
