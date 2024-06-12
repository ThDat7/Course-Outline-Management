package com.dat.controllers;

import com.dat.pojo.*;
import com.dat.service.BaseService;
import com.dat.service.MajorService;
import com.dat.service.StudentService;
import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
@PropertySource("classpath:configs.properties")
public class StudentController extends EntityListController<Student, Integer> {
    @Autowired
    private StudentService studentService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private PasswordGenerator passwordGenerator;
    @Autowired
    private CharacterRule characterRule;

    public StudentController(Environment env, StudentService studentService) {
        super("student",
                "/students",
                "Học sinh",
                List.of("id", "Tên", "Ngành học",
                        "Mssv", "Năm học", "Trạng thái"),
                env, studentService);
    }

    @PostMapping("/pending/accept")
    public String acceptPendingStudent(@ModelAttribute @Valid Student student,
                                       BindingResult rs,
                                       @RequestParam(name = "avatar", required = false) MultipartFile avatar,
                                       Model model) {
        if (rs.hasErrors()) {
            addDetailAttributes(model);
            return "student-detail";
        }

        studentService.acceptPendingStudent(student, avatar);
        return "redirect:/students";
    }

    @Override
    public String detail(Model model, @PathVariable Integer id) {
        String destination = super.detail(model, id);
        Student student = (Student) model.getAttribute("student");
        if (student.getUser().getStatus() == UserStatus.PENDING) {
            student.getUser().setUsername(student.getStudentCode());
            student.getUser().setPassword(
                    passwordGenerator.generatePassword(8, characterRule));
        } else
            student.getUser().setPassword("");
        model.addAttribute("student", student);
        return destination;
    }


    @PostMapping
    public String add(@ModelAttribute @Valid Student student,
                      BindingResult rs,
                      @RequestParam(name = "avatar", required = false) MultipartFile avatar,
                      Model model) {
        if (rs.hasErrors()) {
            addDetailAttributes(model);
            return "student-detail";
        }

        studentService.addOrUpdate(student, avatar);
        return "redirect:/students";
    }

    @Override
    protected List<List> getRecords(Map<String, String> params) {
        List<Student> students = studentService.getAll(params);
        return students.stream().map(s -> List.of(
                        s.getId(),
                        String.format("%s %s", s.getUser().getLastName(), s.getUser().getFirstName()),
                        s.getMajor() == null ? " " : s.getMajor().getName(),
                        s.getStudentCode() == null ? " " : s.getStudentCode(),
                        s.getSchoolYear() == null ? " " : s.getSchoolYear(),
                        s.getUser().getStatus().toString()))
                .collect(Collectors.toList());
    }

    @Override
    protected List<EntityListController<Student, Integer>.Filter> getFilters() {
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
