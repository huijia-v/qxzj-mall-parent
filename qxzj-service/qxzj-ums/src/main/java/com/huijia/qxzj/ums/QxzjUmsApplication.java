package com.huijia.qxzj.ums;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.huijia"})
@MapperScan("com.huijia.qxzj.ums.mapper")
public class QxzjUmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(QxzjUmsApplication.class, args);
    }

}
