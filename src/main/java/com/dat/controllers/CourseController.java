package com.dat.controllers;

import com.dat.pojo.Course;
import com.dat.pojo.Faculty;
import com.dat.service.BaseService;
import com.dat.service.CourseService;
import com.dat.service.FacultyService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
@PropertySource("classpath:configs.properties")
public class CourseController
        extends EntityListController<Course, Integer> {

    private Environment env;
    private CourseService courseService;
    private FacultyService facultyService;

    public CourseController(Environment env, CourseService courseService, FacultyService facultyService) {
        super("course",
                "/courses",
                "Môn học",
                List.of("id", "Tên", "Mã môn", "Số tín chỉ", "Ngành"),
                env, courseService);
        this.courseService = courseService;
        this.env = env;
        this.facultyService = facultyService;
    }

    protected List<List> getRecords(Map<String, String> params) {
        List<Course> courses = courseService.getAll(params);
        return courses.stream().map(course -> List.of(
                        course.getId(),
                        course.getName(),
                        course.getCode(),
                        course.getCredits(),
                        course.getEducationProgramCourses() != null ?
                                course.getEducationProgramCourses().stream()
                                        .map(ep -> ep.getEducationProgram().getMajor().getName())
                                        .collect(Collectors.joining(", "))
                                : ""))
                .collect(Collectors.toList());
    }

    @Override
    protected List<Filter> getFilters() {
        Filter creditsFilter = new Filter("Số tín chỉ", "credits",
                List.of(new FilterItem("1", "1"),
                        new FilterItem("2", "2"),
                        new FilterItem("3", "3"),
                        new FilterItem("4", "4")));

        Filter facultyFilter = new Filter("Khoa", "faculty",
                facultyService.getAll().stream()
                        .map(f -> new FilterItem(f.getName(), f.getId().toString()))
                        .collect(Collectors.toList()));

        return List.of(creditsFilter, facultyFilter);
    }

    @PostMapping
    public String add(Course c) {
        return super.add(c);
    }

    @Override
    public void addAtributes(Model model) {

    }
}
