package com.dat.controllers;

import com.dat.pojo.User;
import com.dat.pojo.UserRole;
import com.dat.pojo.UserStatus;
import com.dat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("statuses", UserStatus.values());
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping
    public String add(@ModelAttribute(value = "user") User user) {
        if (userService.addOrUpdateUser(user) == true)
            return "redirect:/";

        return "users";
    }
}
