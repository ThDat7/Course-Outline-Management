package com.dat.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dat.pojo.*;
import com.dat.repository.TeacherRepository;
import com.dat.repository.UserRepository;
import com.dat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service("userDetailsService")
public class UserServiceImpl
        extends BaseServiceImpl<User, Integer>
        implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

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
//        if (u.getFile() != null && u.getFile().getSize() > 0) {
//            try {
//                Map res = this.cloudinary.uploader().upload(u.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
//                u.setImage(res.get("secure_url").toString());
//            } catch (IOException ex) {
//                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

        return super.addOrUpdate(u);
    }

    @Override
    public List<User> getUserPending(Map<String, String> params) {
        params.put("userStatus", "PENDING");
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        return new com.dat.configs.UserDetails(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser"))
            throw new RuntimeException("Unauthenticated user!");
        return ((com.dat.configs.UserDetails) authentication.getPrincipal()).getUser();
    }

    public void updateAuthCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser"))
            throw new RuntimeException("Unauthenticated user!");
        User user = ((com.dat.configs.UserDetails) authentication.getPrincipal()).getUser();
        User newUser = userRepository.getById(user.getId());
        ((com.dat.configs.UserDetails) authentication.getPrincipal()).setUser(newUser);
    }

    @Override
    public User getByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return passwordEncoder.matches(password, user.getPassword());
    }

    public void updateCurrentUserInfo(User user, MultipartFile avatar) {
        Integer currentUserId = getCurrentUser().getId();

        User oldUser = userRepository.getById(currentUserId);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhone(user.getPhone());
        oldUser.setUsername(user.getUsername());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        try {
            uploadAvatar(oldUser, avatar);
        } catch (IOException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        userRepository.addOrUpdate(oldUser);
        updateAuthCurrentUser();
    }

    public void uploadAvatar(User user, MultipartFile avatar) throws IOException {
        if (avatar != null && avatar.getSize() > 0) {
            Map res = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            user.setImage(res.get("secure_url").toString());
        }
    }
}
