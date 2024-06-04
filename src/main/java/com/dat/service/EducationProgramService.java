package com.dat.service;

import com.dat.dto.AssignOutlineDto;
import com.dat.dto.DataWithCounterDto;
import com.dat.pojo.EducationProgram;

import java.util.List;
import java.util.Map;

public interface EducationProgramService extends BaseService<EducationProgram, Integer> {

    boolean addOrUpdate(EducationProgram educationProgram, List<String> courses);

    DataWithCounterDto<AssignOutlineDto> getReuse(Map<String, String> params);

    DataWithCounterDto<AssignOutlineDto> getNeedCreate(Map<String, String> params);

    void reuseAll(int year);

    int cloneByYear(int year, int byYear);

    DataWithCounterDto<EducationProgram> searchApi(Map<String, String> params);
}
