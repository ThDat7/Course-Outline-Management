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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Year;
import java.util.ArrayList;
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
                        ep.getEducationProgramCourses().size()))
                .collect(Collectors.toList());
    }

    @Override
    protected List<EntityListController<EducationProgram, Integer>.Filter> getFilters() {
        Filter majorFilter = new Filter("Ngành", "major",
                majorService.getAll().stream()
                        .map(m -> new FilterItem(m.getName(), m.getId().toString()))
                        .collect(Collectors.toList()));

        List<FilterItem> yearFilterItems = new ArrayList<>();

        for (int i = Year.now().getValue() + 5; i > 2010; i--)
            yearFilterItems.add(new FilterItem(String.valueOf(i), String.valueOf(i)));

        Filter yearFilter = new Filter("Năm học", "year",
                yearFilterItems);

        return List.of(majorFilter, yearFilter);
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
    public String add(@ModelAttribute("educationProgram") @Valid EducationProgram educationProgram,
                      BindingResult rs,
                      @RequestParam(name = "courses", required = false) List<String> courses,
                      Model model) {
        if (rs.hasErrors()) {
            addDetailAttributes(model);
            return "educationProgram-detail";
        }

        educationProgramService.addOrUpdate(educationProgram, courses);
        return "redirect:/education-programs/";
    }

    @Override
    public String list(Model model, @RequestParam Map<String, String> params) {
        super.list(model, params);
        return "list-educationProgram";
    }

    @GetMapping("/clonebyyear")
    public String cloneByYear(Model model,
                              @RequestParam(name = "year") int year,
                              @RequestParam(name = "ByYear") int byYear) {
        try {
            int recordCloned = educationProgramService.cloneByYear(year, byYear);
            String success = String.format("Clone thành công %d chương trình đào tạo từ năm %d sang năm %d", recordCloned, byYear, year);
            model.addAttribute("success", success);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("year", year);
        return "redirect:/education-programs/";
    }
}
