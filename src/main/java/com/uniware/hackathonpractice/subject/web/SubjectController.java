package com.uniware.hackathonpractice.subject.web;

import com.uniware.hackathonpractice.security.auth.jwt.JwtAuthenticationToken;
import com.uniware.hackathonpractice.security.model.UserContext;
import com.uniware.hackathonpractice.subject.service.SubjectService;
import com.uniware.hackathonpractice.subject.utility.SubjectListDTO;
import com.uniware.hackathonpractice.user.persistence.model.ApplicationUser;
import com.uniware.hackathonpractice.user.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    private SubjectService subjectService;
    private ApplicationUserService applicationUserService;

    @Autowired
    public SubjectController(SubjectService subjectService, ApplicationUserService applicationUserService) {
        this.subjectService = subjectService;
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public SubjectListDTO getAllSubject() {
        SubjectListDTO subjectListDTO = new SubjectListDTO();
        subjectListDTO.setSubjectList(subjectService.findAll());
        return subjectListDTO;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void saveUsersSubjects(@RequestBody SubjectListDTO subjectListDTO, Principal principal) {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
        UserContext userContext = (UserContext) authenticationToken.getPrincipal();
        String loggedInUser = userContext.getUsername();
        ApplicationUser applicationUser = applicationUserService.findByUserName(loggedInUser);
        applicationUser.setSubjects(subjectListDTO.getSubjectList());
        applicationUserService.save(applicationUser);
    }


}
