package com.dat.service;

import com.dat.pojo.CourseOutline;

import java.util.List;

public interface CourseOutlineService extends BaseService<CourseOutline, Integer> {
    boolean addOrUpdate(CourseOutline courseOutline,
                        List<String> types,
                        List<String> methods,
                        List<String> times,
                        List<String> closes,
                        List<Integer> weightPercents,
                        List<Integer> schoolYears);

    boolean update(int id, CourseOutline courseOutline);

    CourseOutline getOrCreateByAssignOutlineId(int assignOutlineId);
}