package com.dat.controllers;

import com.dat.dto.CourseOutlineDto;
import com.dat.pojo.CourseOutline;
import com.dat.service.EducationProgramCourseService;
import com.dat.service.EducationProgramService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assign-outlines")
@CrossOrigin
public class ApiAssignOutlineController {

    @Autowired
    private EducationProgramCourseService educationProgramCourseService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create/{id}")
    public void create(Model model, @PathVariable("id") int epcId,
                       @RequestBody CourseOutlineDto coDto) {
        educationProgramCourseService.associateOutline(epcId, dto2Entity(coDto));
    }

    private CourseOutline dto2Entity(CourseOutlineDto courseOutlineDto) {
        return modelMapper.map(courseOutlineDto, CourseOutline.class);
    }
}
