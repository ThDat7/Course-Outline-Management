package com.dat.controllers;


import com.dat.pojo.Course;
import com.dat.pojo.CourseOutline;
import com.dat.service.CourseOutlineService;
import com.dat.service.CourseService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/course-outlines")
@PropertySource("classpath:configs.properties")
public class CourseOutlineController
        extends EntityListController<CourseOutline, Integer> {

    private Environment env;
    private CourseOutlineService courseOutlineService;

    private CourseService courseService;

    public CourseOutlineController(Environment env, CourseOutlineService courseOutlineService,
                                   CourseService courseService) {
        super("courseOutline", "/course-outlines",
                "Đề cương môn học",
                List.of("id",
                        "Tên môn học",
                        "Năm học",
                        "Trạng thái",
                        "Giáo viên biên soạn"
                ),
                env, courseOutlineService);
        this.courseOutlineService = courseOutlineService;
        this.env = env;
        this.courseService = courseService;
    }

    protected List<List> getRecords(Map<String, String> params) {
        List<CourseOutline> courseOutlines = courseOutlineService.getAll(params);
        return courseOutlines.stream().map(courseOutline -> List.of(
                        courseOutline.getId(),
                        courseOutline.getAssignOutline().getCourse().getName(),
                        courseOutline.getCourseOutlineDetails().stream()
                                .map(detail -> detail.getId().getSchoolYear().toString())
                                .collect(Collectors.joining(", ")),
                        courseOutline.getAssignOutline().getStatus(),
                        String.format("%s %s",
                                courseOutline.getAssignOutline().getTeacher().getUser().getLastName(),
                                courseOutline.getAssignOutline().getTeacher().getUser().getFirstName())
                ))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String add(@ModelAttribute CourseOutline courseOutline,
                      @RequestParam("type") List<String> type,
                      @RequestParam("method") List<String> method,
                      @RequestParam("time") List<String> time,
                      @RequestParam("clos") List<String> clos,
                      @RequestParam("weightPercent") List<Integer> weightPercent,
                      @RequestParam("schoolYears") List<Integer> schoolYears) {
        if (courseOutlineService.addOrUpdate(courseOutline, type, method, time, clos, weightPercent, schoolYears))
            return "redirect:/course-outlines/";

        return "courseOutline-detail";
    }

    @Override
    protected void addAtributes(Model model) {
        Map allCourses = courseService.getAll().stream()
                .collect(Collectors.toMap(Course::getId, Course::getName));
        List<Integer> schoolYears = new ArrayList<>();
        if (((CourseOutline) model.getAttribute("courseOutline"))
                .getCourseOutlineDetails() != null) {
            schoolYears = ((CourseOutline) model.getAttribute("courseOutline"))
                    .getCourseOutlineDetails().stream()
                    .map(detail -> detail.getId().getSchoolYear())
                    .collect(Collectors.toList());
        }

        model.addAttribute("courses", allCourses);
        model.addAttribute("schoolYears", schoolYears);
    }
}
