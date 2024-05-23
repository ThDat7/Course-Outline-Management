/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.controllers;

import com.dat.dto.CourseOutlineDto;
import com.dat.pojo.CourseOutline;
import com.dat.pojo.OutlineStatus;
import com.dat.service.CourseOutlineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author DELL
 */
@RestController
@RequestMapping("/api/course-outlines")
@CrossOrigin
public class ApiCourseOutlineController {
    @Autowired
    private CourseOutlineService courseOutlineService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{assign-id}")
    public ResponseEntity<CourseOutlineDto> get(@PathVariable("assign-id") int assignid) {
        CourseOutline courseOutline = courseOutlineService.getOrCreateByAssignOutlineId(assignid);
        return ResponseEntity.ok(entity2Dto(courseOutline));
    }

    @PostMapping("/{courseOutlineId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("courseOutlineId") int id,
                       @RequestBody CourseOutlineDto courseOutlineDto) {
        courseOutlineService.update(id, dto2Entity(courseOutlineDto));
    }

    private CourseOutlineDto entity2Dto(CourseOutline courseOutline) {
        CourseOutlineDto dto = modelMapper.map(courseOutline, CourseOutlineDto.class);
        if (courseOutline.getStatus() != null)
            dto.setStatus(courseOutline.getStatus().name());
        else dto.setStatus("");
        return dto;
    }

    private CourseOutline dto2Entity(CourseOutlineDto courseOutlineDto) {
        return modelMapper.map(courseOutlineDto, CourseOutline.class);
    }
}
