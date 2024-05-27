package com.dat.controllers;

import com.dat.service.EducationProgramCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/education-programs")
@CrossOrigin
public class ApiEducationProgramController {
    @Autowired
    private EducationProgramCourseService educationProgramCourseService;

    @GetMapping("/remove-outline/{epId}/{courseId}")
    public void removeOutline(@PathVariable("epId") int epId, @PathVariable("courseId") int courseId) {
        educationProgramCourseService.removeOutline(epId, courseId);
    }
}
