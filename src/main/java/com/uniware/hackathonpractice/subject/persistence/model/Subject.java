package com.uniware.hackathonpractice.subject.persistence.model;

import com.uniware.hackathonpractice.user.persistence.model.ApplicationUser;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "subjects")
    private List<ApplicationUser> users = new ArrayList<>();
}
