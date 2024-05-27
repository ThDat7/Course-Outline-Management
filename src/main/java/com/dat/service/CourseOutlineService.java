package com.dat.service;

import com.dat.pojo.CourseOutline;

import java.util.List;
import java.util.Map;

public interface CourseOutlineService extends BaseService<CourseOutline, Integer> {
    boolean addOrUpdate(CourseOutline courseOutline,
                        List<String> types,
                        List<String> methods,
                        List<String> times,
                        List<String> closes,
                        List<Integer> weightPercents,
                        List<Integer> schoolYears);

    boolean update(int id, CourseOutline courseOutline);

    List<CourseOutline> search(Map<String, String> params);

    List<CourseOutline> getReuse(Map<String, String> params);

    List<CourseOutline> getNeedCreate(Map<String, String> params);

    List<CourseOutline> getPending(Map<String, String> params);
}