package com.dat.service.impl;

import com.dat.pojo.Major;
import com.dat.repository.MajorRepository;
import com.dat.service.MajorService;
import org.springframework.stereotype.Service;

@Service
public class MajorServiceImpl
        extends BaseServiceImpl<Major, Integer>
        implements MajorService {

    private MajorRepository majorRepository;

    public MajorServiceImpl(MajorRepository majorRepository) {
        super(majorRepository);
        this.majorRepository = majorRepository;
    }
}
