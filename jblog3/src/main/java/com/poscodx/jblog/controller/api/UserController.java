package com.poscodx.jblog.controller.api;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("userApiController")
@RequestMapping("/user/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/checkid")
    public Object checkId(@RequestParam(value = "id", required = true, defaultValue = "") String id) {
        UserVo vo = userService.duplicateCheckId(id);

        return Map.of("exist", vo != null);
    }
}
