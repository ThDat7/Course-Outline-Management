package com.dat.service.impl;

import com.dat.pojo.Faculty;
import com.dat.pojo.Teacher;
import com.dat.repository.FacultyRepository;
import com.dat.service.FacultyService;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl
        extends BaseServiceImpl<Faculty, Integer>
        implements FacultyService {

    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        super(facultyRepository);
        this.facultyRepository = facultyRepository;
    }

    @Override
    public List<Faculty> getAll() {
        return facultyRepository.getAll();
    }
}
