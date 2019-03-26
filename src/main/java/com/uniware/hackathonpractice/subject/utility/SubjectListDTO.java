package com.uniware.hackathonpractice.subject.utility;

import com.uniware.hackathonpractice.subject.persistence.model.Subject;
import lombok.Data;

import java.util.List;

@Data
public class SubjectListDTO {
    List<Subject> subjectList;
}
