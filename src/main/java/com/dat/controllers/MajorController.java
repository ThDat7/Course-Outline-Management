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

    private CourseService courseService;

    public MajorController(Environment env, MajorService majorService,
                           FacultyService facultyService,
                           CourseService courseService) {
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
        this.courseService = courseService;
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
    protected void addAtributes(Model model) {
        Map facultySelectItems = facultyService.getAll(null).stream()
                .collect(Collectors.toMap(Faculty::getId, Faculty::getName));

        List relationCoursesId = null;
        if (((Major) model.getAttribute("major"))
                .getEducationPrograms() != null)

            relationCoursesId = ((Major) model.getAttribute("major"))
                    .getEducationPrograms().stream()
                    .map(ep -> ep.getId())
                    .collect(Collectors.toList());

        Map allCourses = courseService.getAll().stream()
                .collect(Collectors.toMap(Course::getId, Course::getName));


        model.addAttribute("faculties", facultySelectItems);
        model.addAttribute("relationCoursesId", relationCoursesId);
        model.addAttribute("allCourses", allCourses);
    }

    @PostMapping()
    public String add(@ModelAttribute("major") Major major, @RequestParam(name = "courses", required = false) List<String> courses) {
        if (majorService.addOrUpdate(major, courses))
            return "redirect:/majors/";

        return "major-detail";
    }
}
