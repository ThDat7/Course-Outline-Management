package com.dat.repository;

import com.dat.pojo.Student;
import com.dat.pojo.Teacher;

public interface StudentRepository {
    void add(Student student);

    Student getByUserId(Integer userId);

    void update(Student student);
}
