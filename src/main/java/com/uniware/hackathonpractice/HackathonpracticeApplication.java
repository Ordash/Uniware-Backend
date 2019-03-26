package com.uniware.hackathonpractice;

import com.uniware.hackathonpractice.subject.persistence.model.Subject;
import com.uniware.hackathonpractice.subject.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HackathonpracticeApplication implements CommandLineRunner {

    private SubjectService subjectService;

    @Autowired
    public HackathonpracticeApplication(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public static void main(String[] args) {
        SpringApplication.run(HackathonpracticeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(subjectService.findAll().isEmpty()) {
            List<Subject> subjects = Arrays.asList(
                    new Subject("Math"),
                    new Subject("History"),
                    new Subject("Literature"));

            for (Subject s :subjects) {
                subjectService.save(s);
            }
        }
    }
}
