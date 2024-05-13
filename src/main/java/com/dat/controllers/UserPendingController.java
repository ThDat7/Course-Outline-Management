package com.dat.controllers;

import com.dat.pojo.User;
import com.dat.pojo.UserRole;
import com.dat.pojo.UserStatus;
import com.dat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/users/pending")
public class UserPendingController {
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;

    @GetMapping()
    public String listPending(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("users", userService.getUserPending(params));

        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE"));
        long count = userService.count();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));

        return "users-pending";
    }

    @GetMapping("/{id}")
    public String detailPending(Model model, @PathVariable Integer id) {
        User user = userService.getById(id);
        model.addAttribute("rootEndpoint", "/users/pending");
        model.addAttribute("entityName", "user");
        model.addAttribute("entityLabelName", "Yêu cầu tài khoản");
        model.addAttribute("user", user);
        model.addAttribute("isPending", true);
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("statuses", UserStatus.values());
        return "user-detail";
    }

    @GetMapping("/reject/{id}")
    public String rejectPending(@PathVariable Integer id) {
        userService.rejectPending(id);
        return "redirect:/users/pending/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return "redirect:/users/pending/";
    }

    @PostMapping()
    public String updateAndAccept(@ModelAttribute(value = "user") User user) {
        if (userService.updateAndAcceptUser(user) == true)
            return "redirect:/users/pending/";

        return "user-detail";
    }
}