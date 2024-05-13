package com.dat.repository;

import com.dat.pojo.Major;

import java.util.List;
import java.util.Map;

public interface MajorRepository extends BaseRepository<Major, Integer> {
    List<Major> getAll();
}
