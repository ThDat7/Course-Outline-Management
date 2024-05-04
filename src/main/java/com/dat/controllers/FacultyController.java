package com.dat.controllers;

import com.dat.pojo.Faculty;
import com.dat.service.FacultyService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/faculties")
@PropertySource("classpath:configs.properties")
public class FacultyController extends EntityListController {

    private Environment env;
    private FacultyService facultyService;

    public FacultyController(Environment env, FacultyService facultyService) {
        super("faculties",
                "Khoa",
                List.of("id",
                        "Tên",
                        "Tên viết tắt"),
                env, facultyService);
        this.facultyService = facultyService;
        this.env = env;
    }

    protected List<List> getRecords(Map<String, String> params) {
        List<Faculty> faculties = facultyService.getAll(params);
        return faculties.stream().map(faculty -> List.of(
                        faculty.getId(),
                        faculty.getName(),
                        faculty.getAlias()))
                .collect(Collectors.toList());
    }

    @GetMapping
    public String list(Model model, @RequestParam Map<String, String> params) {
        return super.list(model, params);
    }
}
