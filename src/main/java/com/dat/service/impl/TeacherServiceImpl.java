package com.dat.service.impl;

import com.dat.pojo.Teacher;
import com.dat.repository.BaseRepository;
import com.dat.repository.TeacherRepository;
import com.dat.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl
        extends BaseServiceImpl<Teacher, Integer>
        implements TeacherService {
    private TeacherRepository teacherRepository;

    public TeacherServiceImpl(BaseRepository<Teacher, Integer> repository) {
        super(repository);
    }

    public List<Teacher> getALl() {
        return null;
    }
}
