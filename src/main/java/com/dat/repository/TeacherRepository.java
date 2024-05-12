package com.dat.repository;

import com.dat.pojo.Teacher;

import java.util.List;

public interface TeacherRepository extends BaseRepository<Teacher, Integer> {
    List<Teacher> getAll();
}
