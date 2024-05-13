package com.dat.controllers;

import com.dat.pojo.Course;
import com.dat.pojo.Faculty;
import com.dat.service.BaseService;
import com.dat.service.CourseService;
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

    public CourseController(Environment env, CourseService courseService) {
        super("course",
                "/courses",
                "Môn học",
                List.of("id", "Tên", "Mã môn", "Số tín chỉ", "Ngành"),
                env, courseService);
        this.courseService = courseService;
        this.env = env;
    }

    protected List<List> getRecords(Map<String, String> params) {
        List<Course> courses = courseService.getAll(params);
        return courses.stream().map(course -> List.of(
                        course.getId(),
                        course.getName(),
                        course.getCode(),
                        course.getCredits(),
                        course.getEducationPrograms() != null ?
                                course.getEducationPrograms().stream()
                                        .map(ep -> ep.getMajor().getName())
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

        return List.of(creditsFilter);
    }

    @PostMapping
    public String add(Course c) {
        return super.add(c);
    }

    @Override
    protected void addAtributes(Model model) {

    }
}
