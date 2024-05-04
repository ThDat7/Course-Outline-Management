package com.dat.controllers;

import com.dat.pojo.AssignOutline;
import com.dat.pojo.Major;
import com.dat.service.AssignOutlineService;
import com.dat.service.MajorService;
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
@RequestMapping("/majors")
@PropertySource("classpath:configs.properties")
public class MajorController extends EntityListController {

    private Environment env;
    private MajorService majorService;

    public MajorController(Environment env, MajorService majorService) {
        super("majors",
                "Ngành học",
                List.of("id",
                        "Tên",
                        "Tên viết tắt",
                        "Khoa"),
                env, majorService);
        this.majorService = majorService;
        this.env = env;
    }

    protected List<List> getRecords(Map<String, String> params) {
        List<Major> majors = majorService.getAll(params);
        return majors.stream().map(major -> List.of(
                        major.getId(),
                        major.getName(),
                        major.getAlias(),
                        major.getFaculty().getName()))
                .collect(Collectors.toList());
    }

    @GetMapping
    public String list(Model model, @RequestParam Map<String, String> params) {
        return super.list(model, params);
    }
}
