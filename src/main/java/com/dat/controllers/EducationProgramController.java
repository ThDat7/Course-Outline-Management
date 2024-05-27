package com.dat.controllers;

import com.dat.pojo.Course;
import com.dat.pojo.EducationProgram;
import com.dat.pojo.Faculty;
import com.dat.pojo.Major;
import com.dat.service.BaseService;
import com.dat.service.CourseService;
import com.dat.service.EducationProgramService;
import com.dat.service.MajorService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/education-programs")
@PropertySource("classpath:configs.properties")
public class EducationProgramController
        extends EntityListController<EducationProgram, Integer> {

    private EducationProgramService educationProgramService;

    private MajorService majorService;

    private CourseService courseService;

    public EducationProgramController(Environment env,
                                      EducationProgramService educationProgramService,
                                      MajorService majorService,
                                      CourseService courseService) {
        super("educationProgram", "/education-programs",
                "Chương trình đào tạo",
                List.of("id",
                        "Tên khoa",
                        "Năm học",
                        "Số môn học"),
                env, educationProgramService);
        this.educationProgramService = educationProgramService;
        this.majorService = majorService;
        this.courseService = courseService;
    }

    @Override
    protected List<List> getRecords(Map<String, String> params) {
        List<EducationProgram> educationPrograms = educationProgramService.getAll(params);
        return educationPrograms.stream().map(ep -> List.of(
                        ep.getId(),
                        ep.getMajor().getName(),
                        ep.getSchoolYear(),
                        ""))
                .collect(Collectors.toList());
    }

    @Override
    protected List<EntityListController<EducationProgram, Integer>.Filter> getFilters() {
        return null;
    }

    @Override
    public void addAtributes(Model model) {
        Map majors = majorService.getAll().stream()
                .collect(Collectors.toMap(Major::getId, Major::getName));

        Map allCourses = courseService.getAll().stream()
                .collect(Collectors.toMap(Course::getId, Course::getName));

        model.addAttribute("allCourses", allCourses);
        model.addAttribute("majors", majors);
    }


    @PostMapping()
    public String add(@ModelAttribute("educationProgram") EducationProgram educationProgram, @RequestParam(name = "courses", required = false) List<String> courses) {
        if (educationProgramService.addOrUpdate(educationProgram, courses))
            return "redirect:/education-programs/";

        return "educationProgram-detail";
    }
}
