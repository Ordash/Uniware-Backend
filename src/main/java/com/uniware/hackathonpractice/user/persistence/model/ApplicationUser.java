package com.uniware.hackathonpractice.user.persistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uniware.hackathonpractice.subject.persistence.model.Subject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String password;
    @NotBlank
    @NotNull
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "app_user_subjects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "app_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private List<ApplicationUserRole> roles = new ArrayList<>();

    public void setRoles(List<ApplicationUserRole> roles) {
        this.roles = roles;
    }
}
