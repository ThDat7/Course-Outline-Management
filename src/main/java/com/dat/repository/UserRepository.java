package com.dat.repository;

import com.dat.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserRepository {
    List<User> getUsers(Map<String, String> params);

    Long countUser();

    boolean addOrUpdateUser(User u);
}
