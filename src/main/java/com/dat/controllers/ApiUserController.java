package com.dat.controllers;

import com.dat.dto.ChatUserInfoDto;
import com.dat.dto.TeacherSearchDto;
import com.dat.pojo.Teacher;
import com.dat.pojo.User;
import com.dat.service.TeacherService;
import com.dat.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class ApiUserController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/get-chat-users")
    public ResponseEntity<List<ChatUserInfoDto>> getAllUsers(@RequestParam(name = "ids") List<Integer> ids) {
        List<User> users = userService.getByIds(ids);
        return ResponseEntity.ok(entity2Dto(users));
    }

    @GetMapping("/search-teachers")
    public ResponseEntity<List<TeacherSearchDto>> searchTeachers(@RequestParam(name = "kw") String keyword,
                                                                 @RequestParam(name = "excludedIds", required = false)
                                                                 List<Integer> excludedIds) {
        List<Teacher> teachers = teacherService.search(keyword, excludedIds);
        return ResponseEntity.ok(entity2TeacherDto(teachers));
    }

    private List<ChatUserInfoDto> entity2Dto(List<User> users) {
        return users.stream()
                .map(e -> {
                    ChatUserInfoDto dto = modelMapper.map(e, ChatUserInfoDto.class);
                    dto.setAvatar(e.getImage());
                    dto.setFullName(e.getFirstName() + " " + e.getLastName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private List<TeacherSearchDto> entity2TeacherDto(List<Teacher> teachers) {
        return teachers.stream()
                .map(e -> {
                    TeacherSearchDto dto = modelMapper.map(e, TeacherSearchDto.class);
                    dto.setId(e.getUser().getId());
                    dto.setAvatar(e.getUser().getImage());
                    dto.setFullName(e.getUser().getFirstName() + " " + e.getUser().getLastName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
