package com.dat.controllers;

import com.dat.dto.DataWithCounterDto;
import com.dat.pojo.*;
import com.dat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AssignOutlineController {

    @Autowired
    private Environment env;

    @Autowired
    private EducationProgramService educationProgramService;

    @Autowired
    private EducationProgramCourseService educationProgramCourseService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseOutlineController courseOutlineController;

    @GetMapping
    public String index() {
        return "redirect:/assign-outlines/re-use";
    }

    @GetMapping("/re-use")
    public String getReuseOutline(Model model, @RequestParam Map<String, String> params) {
        DataWithCounterDto assignOutline = educationProgramService.getReuse(params);
        model.addAttribute("assignOutline", assignOutline.getData());
        addAssignOutlineAttributes(model, assignOutline.getTotal());
        return "assign-outline-reuse";
    }

    @GetMapping("/need-create")
    public String getNeedCreateOutline(Model model, @RequestParam Map<String, String> params) {
        DataWithCounterDto assignOutline = educationProgramService.getNeedCreate(params);
        model.addAttribute("assignOutline", assignOutline.getData());
        addAssignOutlineAttributes(model, assignOutline.getTotal());
        return "assign-outline-need-create";
    }

    private void addAssignOutlineAttributes(Model model, Long count) {
        model.addAttribute("majors", majorService.getAll());
        model.addAttribute("courses", courseService.getAll());


        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE"));
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
    }


    @GetMapping("/create/{id}")
    public String getCreateOutline(Model model, @PathVariable("id") int epcId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EducationProgramCourse epc = educationProgramCourseService.getById(epcId);
        CourseOutline co = new CourseOutline();
        co.setCourse(epc.getCourse());
        co.setStatus(OutlineStatus.DOING);

        String view = courseOutlineController.create(model);
        model.addAttribute("rootEndpoint", "/assign-outlines/create/" + epcId);
        model.addAttribute("courseOutline", co);

        return view;
    }

    @PostMapping("/create/{id}")
    public String create(Model model, @PathVariable("id") int epcId,
                         @ModelAttribute CourseOutline courseOutline,
                         @RequestParam(value = "type", required = false) List<String> type,
                         @RequestParam(value = "method", required = false) List<String> method,
                         @RequestParam(value = "time", required = false) List<String> time,
                         @RequestParam(value = "clos", required = false) List<String> clos,
                         @RequestParam(value = "weightPercent", required = false) List<Integer> weightPercent,
                         @RequestParam(value = "schoolYears", required = false) List<Integer> schoolYears) {
        educationProgramCourseService.associateOutline(epcId, courseOutline, type, method, time, clos, weightPercent, schoolYears);
        return "redirect:/assign-outlines/re-use";
    }

    @GetMapping("/re-use/{epc_id}/{co_id}")
    public String reuseOutline(Model model, @PathVariable("epc_id") int epcId, @PathVariable("co_id") int coId) {
        educationProgramCourseService.reuseOutline(epcId, coId);
        return "redirect:/assign-outlines/re-use";
    }

    @GetMapping("/re-use-all/{year}")
    public String reuseAllOutline(@PathVariable("year") int year) {
        educationProgramService.reuseAll(year);
        return "redirect:/assign-outlines/re-use";
    }
}
