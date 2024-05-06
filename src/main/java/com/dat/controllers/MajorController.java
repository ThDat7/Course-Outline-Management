package com.dat.controllers;

import com.dat.pojo.*;
import com.dat.service.FacultyService;
import com.dat.service.MajorService;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/majors")
@PropertySource("classpath:configs.properties")
public class MajorController
        extends EntityListController<Major, Integer> {

    private Environment env;
    private MajorService majorService;
    private FacultyService facultyService;

    public MajorController(Environment env, MajorService majorService, FacultyService facultyService) {
        super("major", "/majors",
                "Ngành học",
                List.of("id",
                        "Tên",
                        "Tên viết tắt",
                        "Khoa"),
                env, majorService);
        this.majorService = majorService;
        this.facultyService = facultyService;
        this.env = env;
    }

    protected List<List> getRecords(Map<String, String> params) {
        List<Major> majors = majorService.getAll(params);
        return majors.stream().map(major -> List.of(
                        major.getId(),
                        major.getName(),
                        major.getAlias(),
                        major.getFaculty().getName()))
                .collect(Collectors.toList());
    }

    @Override
    protected void addAtributes(Model model) {
        List<Faculty> faculties = facultyService.getAll(null);
        Map facultySelectItems = faculties.stream()
                .collect(Collectors.toMap(Faculty::getId, Faculty::getName));


        model.addAttribute("faculties", facultySelectItems);

    }
}
