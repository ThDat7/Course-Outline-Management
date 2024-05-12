package com.dat.service;

import com.dat.pojo.Major;

import java.util.List;

public interface MajorService extends BaseService<Major, Integer> {
    boolean addOrUpdate(Major major, List<String> courses);
}
