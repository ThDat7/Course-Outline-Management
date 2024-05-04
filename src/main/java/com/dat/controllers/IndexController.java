/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.controllers;

import com.dat.DataGenerator;
import com.dat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author PC
 */
@Controller
@PropertySource("classpath:configs.properties")
public class IndexController {

    @Autowired
    private DataGenerator dataGenerator;

    @GetMapping("/")
    public String index() {
        return "forward:/users/";
    }

    @GetMapping("/data")
    public String generateData() {
        dataGenerator.FakeData();
        return "redirect:/";
    }

    @GetMapping("/test")
    public String test() {
        throw new RuntimeException();
//        return "redirect:/";
    }
}

