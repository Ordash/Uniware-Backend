package com.uniware.hackathonpractice.subject.service;

import com.uniware.hackathonpractice.subject.persistence.model.Subject;
import com.uniware.hackathonpractice.subject.persistence.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public void save(Subject subject) {
        subjectRepository.save(subject);
    }

    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }

}
