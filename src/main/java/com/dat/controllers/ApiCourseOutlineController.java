/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.controllers;

import com.dat.dto.*;
import com.dat.pojo.CourseOutline;
import com.dat.pojo.Major;
import com.dat.pojo.OutlineStatus;
import com.dat.service.CourseOutlineService;
import com.dat.service.MajorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("")
    public ResponseEntity<DataWithCounterDto<CourseOutlineDto>> getAll(@RequestParam Map<String, String> params) {
        List<CourseOutline> courseOutlines = courseOutlineService.getByCurrentTeacher(params);
        long total = courseOutlineService.countByCurrentTeacher(params);
        return ResponseEntity.ok(new DataWithCounterDto<>(entity2Dto(courseOutlines), total));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseOutlineDto> get(@PathVariable("id") int id) {
        CourseOutline courseOutline = courseOutlineService.getByCurrentTeacherAndId(id);
        return ResponseEntity.ok(entity2Dto(courseOutline));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<CourseOutlineViewDto> getView(@PathVariable("id") int id) {
        CourseOutline courseOutline = courseOutlineService.getView(id);
        return ResponseEntity.ok(entity2COVDto(courseOutline));
    }

    @PostMapping("/{courseOutlineId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("courseOutlineId") int id,
                       @Valid @RequestBody CourseOutlineEditRequestDto coDto) {
        courseOutlineService.update(id, dto2Entity(coDto));
    }

    @PostMapping
    public void add(@RequestBody CourseOutlineAdminDto coDto) {
        CourseOutline co = dto2Entity(coDto);

        if (!courseOutlineService.addOrUpdate(dto2Entity(coDto)))
            throw new RuntimeException("Add course outline failed");
    }

    private CourseOutlineDto entity2Dto(CourseOutline courseOutline) {
        CourseOutlineDto dto = modelMapper.map(courseOutline, CourseOutlineDto.class);
        if (courseOutline.getStatus() != null)
            dto.setStatus(courseOutline.getStatus().name());
        else dto.setStatus("");
        return dto;
    }

    private CourseOutline dto2Entity(CourseOutlineEditRequestDto courseOutlineDto) {
        return modelMapper.map(courseOutlineDto, CourseOutline.class);
    }

    private CourseOutline dto2Entity(CourseOutlineAdminDto coDto) {
        return modelMapper.map(coDto, CourseOutline.class);
    }


    private List<CourseOutlineDto> entity2Dto(List<CourseOutline> courseOutlines) {
        return courseOutlines.stream()
                .map(co -> modelMapper.map(co, CourseOutlineDto.class))
                .toList();
    }

    private CourseOutlineViewDto entity2COVDto(CourseOutline courseOutline) {
        CourseOutlineViewDto covDto = modelMapper.map(courseOutline, CourseOutlineViewDto.class);
        covDto.setTeacherName(String.format("%s %s",
                courseOutline.getTeacher().getUser().getLastName(),
                courseOutline.getTeacher().getUser().getFirstName()));
        covDto.setYears(courseOutline.getEducationProgramCourses().stream()
                .map(epc -> epc.getEducationProgram().getSchoolYear())
                .collect(Collectors.toList()));
        covDto.setComments(courseOutline.getComments().stream()
                .map(c -> {
                    CommentDto cDto = modelMapper.map(c, CommentDto.class);
                    cDto.setUserFullName(String.format("%s %s",
                            c.getUser().getLastName(),
                            c.getUser().getFirstName()));
                    return cDto;
                })
                .collect(Collectors.toList()));
        return covDto;
    }
}
