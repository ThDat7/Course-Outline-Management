package com.dat.service;

import com.dat.pojo.CourseOutline;

import java.util.List;
import java.util.Map;

public interface CourseOutlineService extends BaseService<CourseOutline, Integer> {
    boolean addOrUpdate(CourseOutline courseOutline);

    boolean update(int id, CourseOutline courseOutline);

    List<CourseOutline> search(Map<String, String> params);
}