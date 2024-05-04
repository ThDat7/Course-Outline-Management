package com.dat.service.impl;

import com.dat.pojo.Faculty;
import com.dat.repository.FacultyRepository;
import com.dat.service.FacultyService;
import org.springframework.stereotype.Service;

@Service
public class FacultyServiceImpl
        extends BaseServiceImpl<Faculty, Integer>
        implements FacultyService {

    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        super(facultyRepository);
        this.facultyRepository = facultyRepository;
    }

}
