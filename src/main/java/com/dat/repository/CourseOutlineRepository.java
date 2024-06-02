package com.dat.repository;

import com.dat.pojo.CourseOutline;

import java.util.List;
import java.util.Map;

public interface CourseOutlineRepository extends BaseRepository<CourseOutline, Integer> {
    List<CourseOutline> getByTeacherId(int teacherId, Map<String, String> params);

    long countByTeacherId(int teacherId, Map<String, String> params);
}
