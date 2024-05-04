package com.dat.service.impl;

import com.dat.pojo.AssignOutline;
import com.dat.repository.AssignOutlineRepository;
import com.dat.service.AssignOutlineService;
import org.springframework.stereotype.Service;

@Service
public class AssignOutlineServiceImpl extends
        BaseServiceImpl<AssignOutline, Integer>
        implements AssignOutlineService {

    private AssignOutlineRepository assignOutlineRepository;

    public AssignOutlineServiceImpl(AssignOutlineRepository repository) {
        super(repository);
        this.assignOutlineRepository = repository;
    }
}
