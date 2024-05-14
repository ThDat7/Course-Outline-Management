package com.dat.controllers;

import com.dat.pojo.*;
import com.dat.service.AssignOutlineService;
import com.dat.service.CourseService;
import com.dat.service.FacultyService;
import com.dat.service.TeacherService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/assign-outlines")
@PropertySource("classpath:configs.properties")
public class AssignOutlineController extends EntityListController<AssignOutline, Integer> {

    private Environment env;
    private AssignOutlineService assignOutlineService;
    private TeacherService teacherService;
    private CourseService courseService;
    private FacultyService facultyService;

    private SimpleDateFormat dateFormat;

    public AssignOutlineController(Environment env, AssignOutlineService assignOutlineService,
                                   TeacherService teacherService,
                                   CourseService courseService,
                                   FacultyService facultyService, SimpleDateFormat dateFormat) {
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
        this.facultyService = facultyService;
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

    @Override
    protected List<Filter> getFilters() {
        List<Filter> filters = new ArrayList<>();
        Filter statusFilter = new Filter("Trạng thái", "status",
                List.of(new FilterItem("Chưa tạo", "NOT_CREATED"),
                        new FilterItem(OutlineStatus.DOING.toString(), OutlineStatus.DOING.name()),
                        new FilterItem(OutlineStatus.COMPLETED.toString(), OutlineStatus.COMPLETED.name())));
        Filter teacherFilter = new Filter("Giáo viên", "teacher",
                teacherService.getAll().stream()
                        .map(t -> new FilterItem(String.format("%s %s",
                                t.getUser().getLastName(),
                                t.getUser().getFirstName()), t.getId().toString()))
                        .collect(Collectors.toList()));

        Filter courseFilter = new Filter("Môn học", "course",
                courseService.getAll().stream()
                        .map(c -> new FilterItem(c.getName(), c.getId().toString()))
                        .collect(Collectors.toList()));
        filters.add(statusFilter);
        filters.add(teacherFilter);
        filters.add(courseFilter);
        return filters;
    }

    @PostMapping
    public String add(AssignOutline ao) {
        return super.add(ao);
    }

    @Override
    protected void addAtributes(Model model) {
        Map teacherSelectItems = teacherService.getAll().stream()
                .collect(Collectors.toMap(Teacher::getId, t -> String.format("%s %s",
                        t.getUser().getLastName(),
                        t.getUser().getFirstName())));
        Map courseSelectItems = courseService.getAll().stream()
                .collect(Collectors.toMap(Course::getId, Course::getName));

        model.addAttribute("teachers", teacherSelectItems);
        model.addAttribute("courses", courseSelectItems);
    }

    @GetMapping("/not-created")
    public String getNotAssign(Model model, @RequestParam Map<String, String> params) {

        List<List> courses = courseService.getCourseNotCreatedAssign(params).stream()
                .map(course -> List.of(
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

        model.addAttribute("entityLabelName", "MÔN HỌC CHƯA PHÂN CÔNG");
        model.addAttribute("labels", List.of("id", "Tên", "Mã môn", "Số tín chỉ", "Ngành"));
        model.addAttribute("records", courses);
        model.addAttribute("filters", getNotCreatedFilter());
//
        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE"));
        long count = courseService.countCourseNotCreatedAssign(params);
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        return "assignOutlines-not-created";
    }

    @GetMapping("/not-created/{courseId}")
    public String createNotAssign(Model model, @PathVariable int courseId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String forwardEndpoint = super.create(model);
        AssignOutline assignOutline = (AssignOutline) model.getAttribute("assignOutline");
        assignOutline.setCourse(courseService.getById(courseId));
        model.addAttribute("assignOutline", assignOutline);
        return forwardEndpoint;
    }

    private List<Filter> getNotCreatedFilter() {
        Filter creditsFilter = new Filter("Số tín chỉ", "credits",
                List.of(new FilterItem("1", "1"),
                        new FilterItem("2", "2"),
                        new FilterItem("3", "3"),
                        new FilterItem("4", "4")));

        Filter facultyFilter = new Filter("Khoa", "faculty",
                facultyService.getAll().stream()
                        .map(f -> new FilterItem(f.getName(), f.getId().toString()))
                        .collect(Collectors.toList()));

        int currentYear = Year.now().getValue();
        List<FilterItem> yearFilterItems = new ArrayList<>();
        for (int i = currentYear + 5; i >= 2015; i--)
            yearFilterItems.add(new FilterItem(String.valueOf(i), String.valueOf(i)));
        Filter yearFilter = new Filter("Năm học", "year", yearFilterItems);

        return List.of(creditsFilter, facultyFilter, yearFilter);
    }

}
