package com.uniware.hackathonpractice.subject.web;

import com.uniware.hackathonpractice.subject.service.SubjectService;
import com.uniware.hackathonpractice.subject.utility.SubjectListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    private SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public SubjectListDTO getAllSubject() {
        SubjectListDTO subjectListDTO = new SubjectListDTO();
        subjectListDTO.setSubjectList(subjectService.findAll());
        return subjectListDTO;
    }


}
