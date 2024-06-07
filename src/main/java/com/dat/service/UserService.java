package com.dat.service;

import com.dat.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService
        extends BaseService<User, Integer>,
        UserDetailsService {

    List<User> getUserPending(Map<String, String> params);

    boolean updateAndAcceptUser(User user);

    void rejectPending(Integer id);

    void updateCurrentUserInfo(User user, MultipartFile avatar);

    void uploadAvatar(User user, MultipartFile avatar) throws IOException;

    User getCurrentUser();

    User getByUserName(String username);

    boolean authenticate(String username, String password);
}
