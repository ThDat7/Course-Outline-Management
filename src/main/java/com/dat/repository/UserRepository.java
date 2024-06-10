package com.dat.repository;

import com.dat.pojo.User;
import com.dat.pojo.UserStatus;

import java.util.List;
import java.util.Map;

public interface UserRepository extends BaseRepository<User, Integer> {
    User findByUsername(String username);

    User findByIdAndStatus(Integer id, UserStatus status);
}
