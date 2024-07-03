package com.poscodx.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    // view resolver를 통해 404 page를 보여주기
    @RequestMapping("/404")
    public String _404() {
        return "errors/404";
    }

    @RequestMapping("/500")
    public String _500() {
        return "errors/500";
    }
}