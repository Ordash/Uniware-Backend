package com.uniware.hackathonpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HackathonpracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackathonpracticeApplication.class, args);
    }

}
