package com.poscodx.jblog.controller;

import com.poscodx.jblog.vo.BlogVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "main/index";
    }
}
