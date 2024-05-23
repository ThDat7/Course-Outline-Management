package com.dat.repository;

import com.dat.pojo.AssignOutline;

import java.util.List;

public interface AssignOutlineRepository extends BaseRepository<AssignOutline, Integer> {
    List<AssignOutline> findByTeacherId(int teacherId);
}
