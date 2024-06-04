package com.dat.service;

import com.dat.dto.DataWithCounterDto;
import com.dat.pojo.CourseOutline;

import java.util.List;
import java.util.Map;

public interface CourseOutlineService extends BaseService<CourseOutline, Integer> {
    boolean addOrUpdate(CourseOutline courseOutline);

    boolean update(int id, CourseOutline courseOutline);

    DataWithCounterDto<CourseOutline> searchApi(Map<String, String> params);

    List<CourseOutline> getByCurrentTeacher(Map<String, String> params);

    long countByCurrentTeacher(Map<String, String> params);

    CourseOutline getView(int id);
}