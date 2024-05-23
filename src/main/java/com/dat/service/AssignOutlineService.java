package com.dat.service;

import com.dat.pojo.AssignOutline;

import java.util.List;

public interface AssignOutlineService extends BaseService<AssignOutline, Integer> {
    List<AssignOutline> getAssigned();
}
