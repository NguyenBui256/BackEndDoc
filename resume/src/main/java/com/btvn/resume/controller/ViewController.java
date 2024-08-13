package com.btvn.resume.controller;

import com.btvn.resume.model.User;
import com.btvn.resume.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class ViewController {

    private final UserService userService;

    @RequestMapping("/searchUserForm")
    public String searchUserForm(Model model){
        model.addAttribute("id");
        return "search-user";
    }

    @RequestMapping("/showUserForm")
    public String showUserForm(@RequestParam("id") int id, Model model){
        User dataUser = userService.findById(id);
        model.addAttribute("name", dataUser.getName());
        model.addAttribute("dob", dataUser.getDob());
        model.addAttribute("gender", dataUser.getGender());
        model.addAttribute("location", dataUser.getLocation());
        model.addAttribute("avatarUrl", dataUser.getAvatarUrl());
        return "show-user";
    }
}
