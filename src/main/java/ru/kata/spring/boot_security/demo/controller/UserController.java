package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/admin/users")
    public String users(@RequestParam(required = false, defaultValue = "0") int id, ModelMap model) {
        if (id == 0) {
            List<User> user = userService.getAllUsers();
            model.addAttribute("users", user);
            return "/userlist.html";
        } else {
            model.addAttribute("user", userService.getById(id));
            return "/userview.html";
        }
    }

    @GetMapping(value = "/admin/users/add")
    public String addUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "/adduser.html";
    }

    @GetMapping(value = "/user")
    public String user(ModelMap model, Principal principal) {
        model.addAttribute("user", userService.getByUsername(principal.getName()));
        return "/user.html";
    }

    @PostMapping(value = "/admin/users/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/admin/users/delete")
    public String addUser(int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/admin/users/update")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }
}
