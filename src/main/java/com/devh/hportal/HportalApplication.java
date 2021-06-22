package com.devh.hportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HportalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HportalApplication.class, args);
    }

}
