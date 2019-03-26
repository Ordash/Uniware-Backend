package com.uniware.hackathonpractice.subject.persistence.repository;

import com.uniware.hackathonpractice.subject.persistence.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
