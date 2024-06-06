package com.dat.controllers;

import com.dat.dto.StudentInfoDto;
import com.dat.dto.TeacherInfoDto;
import com.dat.pojo.Student;
import com.dat.pojo.Teacher;
import com.dat.pojo.User;
import com.dat.service.StudentService;
import com.dat.service.TeacherService;
import com.dat.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin
public class ApiProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/teacher")
    public ResponseEntity<TeacherInfoDto> getTeacherProfile() {
        return ResponseEntity.ok(entity2TeacherInfoDto(teacherService.getProfile()));
    }

    @GetMapping("/student")
    public ResponseEntity<StudentInfoDto> getStudentProfile() {
        return ResponseEntity.ok(entity2StudentInfoDto(studentService.getProfile()));
    }

    @PostMapping("/teacher")
    public void updateTeacherProfile(@RequestBody TeacherInfoDto teacherInfoDto) {
        teacherService.updateProfile(teacherInfoDto2Entity(teacherInfoDto), teacherInfoDto.getAvatar());
    }

    @PostMapping("/student")
    public void updateStudentProfile(@RequestBody StudentInfoDto studentInfoDto,
                                     @RequestParam MultipartFile avatar) {
        studentService.updateProfile(studentInfoDto2Entity(studentInfoDto), avatar);
    }

    @PostMapping(path = "/additional-info/teacher", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public void additionalInfoTeacher(@ModelAttribute TeacherInfoDto teacherInfoDto) {
        teacherService.additionalInfo(teacherInfoDto2Entity(teacherInfoDto), teacherInfoDto.getAvatar());
    }

    @PostMapping("/additional-info/student")
    public void additionalInfoStudent(@RequestParam MultipartFile avatar) {
        studentService.additionalStudentInfo(avatar);
    }

    private TeacherInfoDto entity2TeacherInfoDto(Teacher teacher) {
        TeacherInfoDto userDto = modelMapper.map(teacher.getUser(), TeacherInfoDto.class);
        userDto.setMajorId(teacher.getMajor().getId());
        return userDto;
    }

    private StudentInfoDto entity2StudentInfoDto(Student student) {
        StudentInfoDto userDto = modelMapper.map(student.getUser(), StudentInfoDto.class);
        userDto.setStudentCode(student.getStudentCode());
        return userDto;
    }

    private Teacher teacherInfoDto2Entity(TeacherInfoDto teacherInfoDto) {
        User user = modelMapper.map(teacherInfoDto, User.class);
        Teacher teacher = modelMapper.map(teacherInfoDto, Teacher.class);
        teacher.setUser(user);
        return teacher;
    }

    private Student studentInfoDto2Entity(StudentInfoDto studentInfoDto) {
        User user = modelMapper.map(studentInfoDto, User.class);
        Student student = modelMapper.map(studentInfoDto, Student.class);
        student.setUser(user);
        return student;
    }
}
