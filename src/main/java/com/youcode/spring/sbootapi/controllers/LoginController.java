package com.youcode.spring.sbootapi.controllers;

import com.youcode.spring.sbootapi.forms.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/users")
public class LoginController {

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("form", new LoginForm());
        return "users/login";
    }

}
