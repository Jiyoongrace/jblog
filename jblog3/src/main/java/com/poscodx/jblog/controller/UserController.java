package com.poscodx.jblog.controller;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/join")
    public String join(@ModelAttribute UserVo vo) {
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
        if(result.hasErrors()) {
            Map<String, Object> map = result.getModel();
            model.addAllAttributes(map);

            return "user/join";
        }
        userService.join(vo);
        return "redirect:/user/joinsuccess";
    }

    @GetMapping("/joinsuccess")
    public String joinsuccess() {
        return "user/joinsuccess";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }
}
