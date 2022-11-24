package com.huijia.qxzj.portal.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = {"com.huijia"})
@MapperScan("com.huijia.qxzj.ums.mapper")
public class QxzjPortalWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxzjPortalWebApplication.class, args);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
