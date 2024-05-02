package com.dat.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dat.pojo.User;
import com.dat.repository.UserRepository;
import com.dat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<User> getUsers(Map<String, String> params) {
        return userRepository.getUsers(params);
    }

    @Override
    public Long countUser() {
        return userRepository.countUser();
    }

    @Override
    public boolean addOrUpdateUser(User u) {
        if (u.getFile() != null && u.getFile().getSize() > 0) {
            try {
                Map res = this.cloudinary.uploader().upload(u.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return userRepository.addOrUpdateUser(u);
    }
}
