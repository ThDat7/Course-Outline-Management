package com.dat.controllers;

import com.dat.components.JwtService;
import com.dat.dto.UserLoginDto;
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
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        if (userService.authenticate(userLoginDto.getUsername(), userLoginDto.getPassword())) {
            String token = this.jwtService.generateTokenLogin(userLoginDto.getUsername());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials");
    }
}
