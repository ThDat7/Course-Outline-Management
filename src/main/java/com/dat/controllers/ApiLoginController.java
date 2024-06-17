package com.dat.controllers;

import com.dat.components.JwtService;
import com.dat.dto.UserLoginRequestDto;
import com.dat.dto.UserLoginResponseDto;
import com.dat.pojo.User;
import com.dat.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<UserLoginResponseDto> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        if (userService.authenticate(userLoginRequestDto.getUsername(), userLoginRequestDto.getPassword())) {
            String token = this.jwtService.generateTokenLogin(userLoginRequestDto.getUsername());
            User user = userService.getByUserName(userLoginRequestDto.getUsername());
            String firebaseToken = generateFirebaseToken(user.getId());
            return ResponseEntity.ok(convertToDto(token, firebaseToken, user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    private String generateFirebaseToken(Integer userId) {
        try {
            String firebaseToken = FirebaseAuth.getInstance().createCustomToken(userId.toString());
            return firebaseToken;
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return "Error creating firebase token";
        }
    }

    private UserLoginResponseDto convertToDto(String token, String firebaseToken, User user) {
        UserLoginResponseDto ulrd = new UserLoginResponseDto();
        ulrd.setToken(token);
        ulrd.setFirebaseToken(firebaseToken);
        ulrd.setId(user.getId());
        ulrd.setFullName(String.format("%s %s", user.getLastName(), user.getFirstName()));
        ulrd.setRole(user.getRole().name());
        ulrd.setStatus(user.getStatus().name());
        ulrd.setAvatar(user.getImage());
        return ulrd;
    }
}
