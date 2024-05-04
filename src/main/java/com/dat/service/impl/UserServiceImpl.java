package com.dat.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dat.pojo.User;
import com.dat.pojo.UserStatus;
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
public class UserServiceImpl
        extends BaseServiceImpl<User, Integer>
        implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    public UserServiceImpl(UserRepository userRepository, Cloudinary cloudinary) {
        super(userRepository);
        this.cloudinary = cloudinary;
        this.userRepository = userRepository;
    }

    @Override
    public boolean addOrUpdate(User u) {
        if (u.getFile() != null && u.getFile().getSize() > 0) {
            try {
                Map res = this.cloudinary.uploader().upload(u.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return super.addOrUpdate(u);
    }

    @Override
    public List<User> getUserPending(Map<String, String> params) {
        params.replace("userStatus", "PENDING");
        return super.getAll(params);
    }

    @Override
    public boolean updateAndAcceptUser(User user) {
        user.setStatus(UserStatus.NEED_INFO);
        return super.addOrUpdate(user);
    }

    @Override
    public void rejectPending(Integer id) {
        userRepository.updateStatus(id, UserStatus.DISABLED);
    }


}
