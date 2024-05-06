package com.dat.controllers;

import com.dat.pojo.AssignOutline;
import com.dat.pojo.Course;
import com.dat.service.AssignOutlineService;
import com.dat.service.CourseService;
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
@RequestMapping("/assign-outlines")
@PropertySource("classpath:configs.properties")
public class AssignOutlineController extends EntityListController<AssignOutline, Integer> {

    private Environment env;
    private AssignOutlineService assignOutlineService;

    public AssignOutlineController(Environment env, AssignOutlineService assignOutlineService) {
        super("assignOutline", "/assign-outlines",
                "Phân công đề cương",
                List.of("id",
                        "Giáo viên",
                        "Môn học",
                        "Trạng thái",
                        "Ngày hết hạn",
                        "Ngày phân công"),
                env, assignOutlineService);
        this.assignOutlineService = assignOutlineService;
        this.env = env;
    }

    protected List<List> getRecords(Map<String, String> params) {
        List<AssignOutline> assignOutlines = assignOutlineService.getAll(params);
        return assignOutlines.stream().map(assignOutline -> List.of(
                        assignOutline.getId(),
                        String.format("%s %s",
                                assignOutline.getTeacher().getUser().getLastName(),
                                assignOutline.getTeacher().getUser().getFirstName()),
                        assignOutline.getCourse().getName(),
                        assignOutline.getStatus(),
                        assignOutline.getDeadlineDate(),
                        assignOutline.getAssignDate()))
                .collect(Collectors.toList());
    }

    @Override
    protected void addAtributes(Model model) {
        
    }
}
