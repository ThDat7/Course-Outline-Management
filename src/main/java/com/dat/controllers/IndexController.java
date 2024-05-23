/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.controllers;

import com.dat.DataGenerator;
import com.dat.pojo.*;
import com.dat.service.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * @author PC
 */
@Controller
@PropertySource("classpath:configs.properties")
public class IndexController {

    @Autowired
    private DataGenerator dataGenerator;


    @Autowired
    private LocalSessionFactoryBean factory;

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
    @Transactional
    public String test() {
        Session s = factory.getObject().getCurrentSession();

        Faculty faculty = new Faculty();
        faculty.setName("Khoa CNTT");
        s.save(faculty);

        Major major = new Major();
        major.setName("Công nghệ thông tin");
        major.setFaculty(faculty);
        s.save(major);

        Course course = new Course();
        course.setName("Lập trình Java");
        s.save(course);

        EducationProgram educationProgram = new EducationProgram();
        EducationProgramId id = new EducationProgramId();
        educationProgram.setId(id);
        educationProgram.setCourse(new Course(course.getId()));
        major.setEducationPrograms(Set.of(educationProgram));
        s.update(major);

        return null;
    }
}

