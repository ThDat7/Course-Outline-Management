package com.dat.service.impl;

import com.dat.pojo.Teacher;
import com.dat.repository.BaseRepository;
import com.dat.repository.TeacherRepository;
import com.dat.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl
        extends BaseServiceImpl<Teacher, Integer>
        implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherServiceImpl(BaseRepository<Teacher, Integer> repository) {
        super(repository);
    }

    public List<Teacher> getAll() {
        return teacherRepository.getAll();
    }
}
