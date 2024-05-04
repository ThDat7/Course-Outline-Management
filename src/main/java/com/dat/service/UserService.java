package com.dat.service;

import com.dat.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService extends BaseService<User, Integer> {

    List<User> getUserPending(Map<String, String> params);

    boolean updateAndAcceptUser(User user);

    void rejectPending(Integer id);
}
