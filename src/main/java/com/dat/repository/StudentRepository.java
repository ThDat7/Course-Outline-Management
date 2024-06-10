package com.dat.repository;

import com.dat.pojo.Student;
import com.dat.pojo.Teacher;

public interface StudentRepository extends BaseRepository<Student, Integer> {
    void add(Student student);

    Student getByUserId(Integer userId);

    void update(Student student);

    void delete(Integer id);
}
