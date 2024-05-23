package com.dat.service;

import com.dat.pojo.Major;

import java.util.List;
import java.util.Map;

public interface MajorService extends BaseService<Major, Integer> {
    boolean addOrUpdate(Major major, List<String> courses);

    List<Major> searchEducationPrograms(Map<String, String> params);

    List<Major> getAll();
}
