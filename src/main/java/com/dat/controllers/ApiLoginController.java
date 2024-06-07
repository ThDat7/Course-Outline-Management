package com.dat.controllers;

import com.dat.components.JwtService;
import com.dat.dto.UserLoginRequestDto;
import com.dat.dto.UserLoginResponseDto;
import com.dat.pojo.User;
import com.dat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @RequestMapping
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        if (userService.authenticate(userLoginRequestDto.getUsername(), userLoginRequestDto.getPassword())) {
            String token = this.jwtService.generateTokenLogin(userLoginRequestDto.getUsername());
            User user = userService.getByUserName(userLoginRequestDto.getUsername());
            return ResponseEntity.ok(convertToDto(token, user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    private UserLoginResponseDto convertToDto(String token, User user) {
        UserLoginResponseDto ulrd = new UserLoginResponseDto();
        ulrd.setToken(token);
        ulrd.setFullName(String.format("%s %s", user.getLastName(), user.getFirstName()));
        ulrd.setRole(user.getRole().name());
        ulrd.setAvatar(user.getImage());
        return ulrd;
    }
}
