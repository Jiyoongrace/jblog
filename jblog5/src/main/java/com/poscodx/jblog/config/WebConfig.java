package com.poscodx.jblog.config;

import com.poscodx.jblog.config.web.FileUploadConfig;
import com.poscodx.jblog.config.web.MvcConfig;
import com.poscodx.jblog.config.web.SecurityConfig;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy
@Import({MvcConfig.class, SecurityConfig.class, FileUploadConfig.class})
@ComponentScan({"com.poscodx.jblog.controller", "com.poscodx.jblog.exception"})
public class WebConfig implements WebMvcConfigurer {
}
