package com.dat.controllers;

import com.dat.pojo.Major;
import com.dat.pojo.Teacher;
import com.dat.pojo.UserRole;
import com.dat.pojo.UserStatus;
import com.dat.service.MajorService;
import com.dat.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/teachers")
@PropertySource("classpath:configs.properties")
public class TeacherController extends EntityListController<Teacher, Integer> {
    private TeacherService teacherService;
    @Autowired
    private MajorService majorService;

    public TeacherController(Environment env, TeacherService teacherService) {
        super("teacher",
                "/teachers",
                "Giảng viên",
                List.of("id", "Tên", "Ngành", "Trạng thái"),
                env, teacherService);
        this.teacherService = teacherService;
    }

    @PostMapping("/pending/accept")
    public String acceptPendingStudent(@ModelAttribute Teacher teacher, @RequestParam(name = "avatar", required = false) MultipartFile avatar) {
        teacherService.acceptPending(teacher, avatar);
        return "redirect:/teachers";
    }

    @Override
    public String detail(Model model, @PathVariable Integer id) {
        String destination = super.detail(model, id);
        Teacher teacher = (Teacher) model.getAttribute("teacher");
        teacher.getUser().setPassword("");
        model.addAttribute("teacher", teacher);
        return destination;
    }
    @Override
    protected List<List> getRecords(Map<String, String> params) {
        List<Teacher> teachers = teacherService.getAll(params);
        return teachers.stream().map(t -> List.of(
                        t.getId(),
                        String.format("%s %s", t.getUser().getLastName(), t.getUser().getFirstName()),
                        t.getMajor().getName(),
                        t.getUser().getStatus().toString()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String add(@ModelAttribute Teacher teacher, @RequestParam(name = "avatar", required = false) MultipartFile avatar) {
        teacherService.addOrUpdate(teacher, avatar);
        return "redirect:/teachers";
    }

    @Override
    protected List<EntityListController<Teacher, Integer>.Filter> getFilters() {
        Filter majorFilter = new Filter("Ngành", "major",
            majorService.getAll().stream()
                    .map(m-> new FilterItem(m.getName(), m.getId().toString()))
                    .collect(Collectors.toList()));
        Filter statusFilter = new Filter("Trạng thái", "status",
                Arrays.stream(UserStatus.values()).map(s -> new FilterItem(s.toString(), s.name()))
                        .collect(Collectors.toList()));

        return List.of(majorFilter, statusFilter);
    }

    @Override
    public void addAtributes(Model model) {
        model.addAttribute("statusList", UserStatus.values());
        model.addAttribute("roleList", UserRole.values());
        List<Major> majorList = majorService.getAll();
        model.addAttribute("majorList", majorList);
    }
}
