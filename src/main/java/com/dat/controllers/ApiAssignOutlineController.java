package com.dat.controllers;

import com.dat.dto.AssignOutlineDto;
import com.dat.pojo.AssignOutline;
import com.dat.service.AssignOutlineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assign-outlines")
@CrossOrigin
public class ApiAssignOutlineController {
    @Autowired
    private AssignOutlineService assignOutlineService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<AssignOutlineDto>> getAssigned() {
        List<AssignOutline> assignOutlines = assignOutlineService.getAssigned();
        return ResponseEntity.ok(entity2Dto(assignOutlines));
    }

    private List<AssignOutlineDto> entity2Dto(List<AssignOutline> assignOutlines) {
        return assignOutlines.stream()
                .map(assignOutline -> modelMapper.map(assignOutline, AssignOutlineDto.class))
                .collect(Collectors.toList());
    }
}
