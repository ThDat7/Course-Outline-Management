package com.dat.controllers;

import com.dat.dto.CourseOutlineSearchDto;
import com.dat.dto.DataWithCounterDto;
import com.dat.dto.EducationProgramSearchDto;
import com.dat.pojo.CourseOutline;
import com.dat.pojo.EducationProgram;
import com.dat.service.CourseOutlineService;
import com.dat.service.EducationProgramService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
@CrossOrigin
public class ApiSearchController {
    @Autowired
    private CourseOutlineService courseOutlineService;
    @Autowired
    private EducationProgramService educationProgramService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<List<DataWithCounterDto>> search(@RequestParam Map<String, String> params) {
        DataWithCounterDto<CourseOutline> dwcCourseOutlines = courseOutlineService.searchApi(params);
        DataWithCounterDto<EducationProgram> dwcEducationProgram = educationProgramService.searchApi(params);

        DataWithCounterDto<CourseOutlineSearchDto> dwcCourseOutlinesDto = new DataWithCounterDto<>(
                courseOutline2Dto(dwcCourseOutlines.getData()), dwcCourseOutlines.getTotal());
        DataWithCounterDto<EducationProgramSearchDto> dwcEducationProgramDto = new DataWithCounterDto<>(
                educationProgram2Dto(dwcEducationProgram.getData()), dwcEducationProgram.getTotal());

        return ResponseEntity.ok(List.of(dwcCourseOutlinesDto, dwcEducationProgramDto));
    }

    private List<EducationProgramSearchDto> educationProgram2Dto(List<EducationProgram> eps) {
        return eps.stream().map(ep -> modelMapper.map(ep, EducationProgramSearchDto.class))
                .collect(Collectors.toList());
    }

    private List<CourseOutlineSearchDto> courseOutline2Dto(List<CourseOutline> cos) {
        return cos.stream().map(co -> {
            CourseOutlineSearchDto coDto = modelMapper.map(co, CourseOutlineSearchDto.class);
            coDto.setYears(co.getEducationProgramCourses().stream()
                    .map(epc -> epc.getEducationProgram().getSchoolYear())
                    .collect(Collectors.toList()));
            coDto.setTeacherName(String.format("%s %s",
                    co.getTeacher().getUser().getLastName(),
                    co.getTeacher().getUser().getFirstName()));
            return coDto;
        }).collect(Collectors.toList());
    }
}
