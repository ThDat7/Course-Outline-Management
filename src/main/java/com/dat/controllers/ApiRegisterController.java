package com.dat.controllers;

import com.dat.dto.MajorDto;
import com.dat.dto.StudentInfoDto;
import com.dat.dto.TeacherInfoDto;
import com.dat.pojo.Major;
import com.dat.pojo.Student;
import com.dat.pojo.Teacher;
import com.dat.pojo.User;
import com.dat.service.MajorService;
import com.dat.service.StudentService;
import com.dat.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/register")
@CrossOrigin
public class ApiRegisterController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private MajorService majorService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/majors")
    public ResponseEntity<List<MajorDto>> getFaculties() {
        return ResponseEntity.ok(entity2majorDto(majorService.getAll()));
    }

    @PostMapping("/student")
    public void studentRegister(@RequestBody StudentInfoDto studentInfoDto) {
        studentService.studentRegister(studentDto2User(studentInfoDto));
    }

    @PostMapping(path = "/teacher", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public void teacherRegister(@ModelAttribute TeacherInfoDto teacherInfoDto) {
        teacherService.teacherRegister(teacherDto2User(teacherInfoDto),
                teacherInfoDto.getMajorId(), teacherInfoDto.getAvatar());
    }

    private Student studentDto2User(StudentInfoDto studentInfoDto) {
        User user = modelMapper.map(studentInfoDto, User.class);
        Student student = modelMapper.map(studentInfoDto, Student.class);
        student.setUser(user);
        return student;
    }

    private Teacher teacherDto2User(TeacherInfoDto teacherInfoDto) {
        User user = modelMapper.map(teacherInfoDto, User.class);
        Teacher teacher = modelMapper.map(teacherInfoDto, Teacher.class);
        teacher.setUser(user);
        return teacher;
    }

    private List<MajorDto> entity2majorDto(List<Major> majors) {
//        modelMapper.
        return majors.stream().map(m -> modelMapper.map(m, MajorDto.class)).toList();
    }
}
