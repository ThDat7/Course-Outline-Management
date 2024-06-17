package com.dat.repository;

import com.dat.pojo.Teacher;
import com.dat.pojo.User;
import com.dat.pojo.UserStatus;

import java.util.List;

public interface TeacherRepository extends BaseRepository<Teacher, Integer> {
    List<Teacher> getAll();

    Teacher getByUserId(int userId);

    Teacher getByUserIdAndUserStatus(Integer id, UserStatus status);

    List<Teacher> search(String keyword, List<Integer> excludedIds, int limit);
}
