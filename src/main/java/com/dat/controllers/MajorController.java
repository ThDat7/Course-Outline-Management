package com.dat.controllers;

import com.dat.pojo.*;
import com.dat.service.CourseService;
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
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/majors")
@PropertySource("classpath:configs.properties")
public class MajorController
        extends EntityListController<Major, Integer> {

    private Environment env;
    private MajorService majorService;
    private FacultyService facultyService;

    public MajorController(Environment env, MajorService majorService,
                           FacultyService facultyService) {
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
    protected List<Filter> getFilters() {
        Filter facultyFilter = new Filter("Khoa", "faculty",
                facultyService.getAll().stream()
                        .map(f -> new FilterItem(f.getName(), f.getId().toString()))
                        .collect(Collectors.toList()));
        return List.of(facultyFilter);
    }

    @Override
    public void addAtributes(Model model) {
        Map facultySelectItems = facultyService.getAll().stream()
                .collect(Collectors.toMap(Faculty::getId, Faculty::getName));


        model.addAttribute("faculties", facultySelectItems);
    }

    @PostMapping()
    public String add(@ModelAttribute("major") Major major) {
        return super.add(major);
    }
}
