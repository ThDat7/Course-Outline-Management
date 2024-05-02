/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author DELL
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiCourseOutlineController {

    @GetMapping
    public ResponseEntity<?> index() {
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> testCreate(@RequestBody String content) {
        return new ResponseEntity<String>(content, HttpStatus.OK);
    }
}
