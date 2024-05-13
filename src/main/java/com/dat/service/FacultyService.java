package com.dat.service;

import com.dat.pojo.Faculty;

import java.util.List;

public interface FacultyService extends BaseService<Faculty, Integer> {
    List<Faculty> getAll();
}
