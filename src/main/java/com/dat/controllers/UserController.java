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
@PropertySource("classpath:configs.properties")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;

    @GetMapping("/")
    public String list(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("users", userService.getAll(params));

        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE"));
        long count = userService.count();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));

        return "users";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("statuses", UserStatus.values());
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user-detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("statuses", UserStatus.values());
        model.addAttribute("user", new User());
        return "user-detail";
    }

    @PostMapping
    public String add(@ModelAttribute(value = "user") User user) {
        if (userService.addOrUpdate(user) == true)
            return "redirect:/users/";

        return "user-detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.delete(id);
        return "redirect:/users/";
    }


    @GetMapping("/pending/")
    public String listPending(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("users", userService.getUserPending(params));

        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE"));
        long count = userService.count();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));

        return "users-pending";
    }

    @GetMapping("/pending/{id}")
    public String detailPending(Model model, @PathVariable Integer id) {
        model.addAttribute("isPending", true);
        return detail(model, id);
    }

    @GetMapping("/pending/reject/{id}")
    public String rejectPending(@PathVariable Integer id) {
        userService.rejectPending(id);
        return "redirect:/users/pending/";
    }

    @PostMapping("/pending/")
    public String updateAndAccept(@ModelAttribute(value = "user") User user) {
        if (userService.updateAndAcceptUser(user) == true)
            return "redirect:/users/pending/";

        return "user-detail";
    }
}
