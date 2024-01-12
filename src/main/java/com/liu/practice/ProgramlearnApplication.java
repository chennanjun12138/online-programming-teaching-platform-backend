package com.liu.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.liu.practice.dao")
public class ProgramlearnApplication {

    private static String active;

    @Value("${spring.profiles.active}")
    public void setActive(String active) {
        this.active = active;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProgramlearnApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProgramlearnApplication.class, args);
        logger.info("项目启动成功!!!环境:"+active);
    }

}
