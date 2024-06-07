package com.dat.repository;

import com.dat.dto.DataWithCounterDto;
import com.dat.pojo.CourseOutline;
import com.dat.pojo.OutlineStatus;

import java.util.List;
import java.util.Map;

public interface CourseOutlineRepository extends BaseRepository<CourseOutline, Integer> {
    List<CourseOutline> getByUserIdCompile(int userId, Map<String, String> params);

    long countByTeacherId(int teacherId, Map<String, String> params);

    DataWithCounterDto<CourseOutline> searchApi(Map<String, String> params);

    CourseOutline getView(int id);

    Boolean existByUserIdCompileAndStatus(int id, int currentUserId, OutlineStatus doing);

    CourseOutline getByUserIdCompileAndId(int currentUserId, int id);
}
