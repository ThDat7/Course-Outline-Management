package com.dat.service;

import com.dat.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUsers(Map<String, String> params);

    Long countUser();

    boolean addOrUpdateUser(User u);
}
