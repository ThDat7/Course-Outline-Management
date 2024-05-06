package com.dat.controllers;


import com.dat.pojo.AssignOutline;
import com.dat.pojo.CourseOutline;
import com.dat.service.AssignOutlineService;
import com.dat.service.CourseOutlineService;
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
@RequestMapping("/course-outlines")
@PropertySource("classpath:configs.properties")
public class CourseOutlineController
        extends EntityListController<CourseOutline, Integer> {

    private Environment env;
    private CourseOutlineService courseOutlineService;

    public CourseOutlineController(Environment env, CourseOutlineService courseOutlineService) {
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

    @Override
    protected void addAtributes(Model model) {

    }
}
