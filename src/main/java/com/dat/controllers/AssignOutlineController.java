package com.dat.controllers;

import com.dat.pojo.AssignOutline;
import com.dat.pojo.Course;
import com.dat.pojo.Teacher;
import com.dat.service.AssignOutlineService;
import com.dat.service.CourseService;
import com.dat.service.TeacherService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/assign-outlines")
@PropertySource("classpath:configs.properties")
public class AssignOutlineController extends EntityListController<AssignOutline, Integer> {

    private Environment env;
    private AssignOutlineService assignOutlineService;
    private TeacherService teacherService;
    private CourseService courseService;
    private SimpleDateFormat dateFormat;

    public AssignOutlineController(Environment env, AssignOutlineService assignOutlineService,
                                   TeacherService teacherService,
                                   CourseService courseService, SimpleDateFormat dateFormat) {
        super("assignOutline", "/assign-outlines",
                "Phân công đề cương",
                List.of("id",
                        "Giáo viên",
                        "Môn học",
                        "Trạng thái",
                        "Ngày phân công",
                        "Hạn chót"),
                env, assignOutlineService);
        this.assignOutlineService = assignOutlineService;
        this.env = env;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.dateFormat = dateFormat;
    }

    protected List<List> getRecords(Map<String, String> params) {
        List<AssignOutline> assignOutlines = assignOutlineService.getAll(params);
        return assignOutlines.stream().map(assignOutline -> List.of(
                        assignOutline.getId(),
                        String.format("%s %s",
                                assignOutline.getTeacher().getUser().getLastName(),
                                assignOutline.getTeacher().getUser().getFirstName()),
                        assignOutline.getCourse().getName(),
                        assignOutline.getCourseOutline() == null ? "Chưa tạo" :
                                assignOutline.getCourseOutline().getStatus().toString(),
                        dateFormat.format(assignOutline.getAssignDate()),
                        dateFormat.format(assignOutline.getDeadlineDate())))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String add(AssignOutline ao) {
        return super.add(ao);
    }

    @Override
    protected void addAtributes(Model model) {
        Map teacherSelectItems = teacherService.getAll(null).stream()
                .collect(Collectors.toMap(Teacher::getId, t -> String.format("%s %s",
                        t.getUser().getLastName(),
                        t.getUser().getFirstName())));
        Map courseSelectItems = courseService.getAll().stream()
                .collect(Collectors.toMap(Course::getId, Course::getName));

        model.addAttribute("teachers", teacherSelectItems);
        model.addAttribute("courses", courseSelectItems);
    }
}
