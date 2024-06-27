package com.poscodx.jblog.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler({IllegalArgumentException.class, Exception.class})
    public String handler(Exception e, Model model) {

        // 1. 로깅(logging)
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        logger.error(errors);

        // 2. 사과(종료)
        model.addAttribute("error", "잘못된 URL 접근입니다.");
        return "errors/exception";
    }
}
