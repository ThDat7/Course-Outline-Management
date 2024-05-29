package com.dat.repository;

import com.dat.dto.AssignOutlineDto;
import com.dat.dto.DataWithCounterDto;
import com.dat.pojo.EducationProgram;

import java.util.List;
import java.util.Map;

public interface EducationProgramRepository extends BaseRepository<EducationProgram, Integer> {

    DataWithCounterDto<AssignOutlineDto> getReuse(Map<String, String> params);

    DataWithCounterDto<AssignOutlineDto> getNeedCreate(Map<String, String> params);

    void reuseAll(int year);

    EducationProgram add(EducationProgram educationProgram);

    int cloneByYear(int year, int byYear);

    Long countByYear(int byYear);
}
