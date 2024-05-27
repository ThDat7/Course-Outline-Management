package com.dat.service.impl;

import com.dat.pojo.Course;
import com.dat.pojo.EducationProgram;
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

    @Override
    public List<Major> searchEducationPrograms(Map<String, String> params) {
        return null;
    }

    public List<Major> getAll() {
        return majorRepository.getAll();
    }
}
