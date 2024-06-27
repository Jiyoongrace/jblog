package com.poscodx.jblog.config;

import com.poscodx.jblog.config.app.DBConfig;
import com.poscodx.jblog.config.app.MyBatisConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy // auto proxy
@EnableTransactionManagement // Transaction AOP Proxy
@ComponentScan({"com.poscodx.jblog.service", "com.poscodx.jblog.repository"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {
}
