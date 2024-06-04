package com.dat.controllers;

import com.dat.dto.EducationProgramViewDto;
import com.dat.pojo.EducationProgram;
import com.dat.service.EducationProgramCourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/education-programs")
@CrossOrigin
public class ApiEducationProgramController {
    @Autowired
    private EducationProgramCourseService educationProgramCourseService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/remove-outline/{epId}/{courseId}")
    public void removeOutline(@PathVariable("epId") int epId, @PathVariable("courseId") int courseId) {
        educationProgramCourseService.removeOutline(epId, courseId);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<EducationProgramViewDto> getView(@PathVariable int id) {
        EducationProgram educationProgram = educationProgramCourseService.getView(id);
        return ResponseEntity.ok(entity2Dto(educationProgram));
    }

    private EducationProgramViewDto entity2Dto(EducationProgram educationProgram) {
        EducationProgramViewDto educationProgramViewDto = modelMapper.map(educationProgram, EducationProgramViewDto.class);
        return educationProgramViewDto;
    }
}
