package com.uniware.hackathonpractice.user.persistence.model;

import com.uniware.hackathonpractice.user.util.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class ApplicationUserRole {

    @Id
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role roleEnum;

    @ManyToMany(mappedBy = "roles")
    private List<ApplicationUser> user = new ArrayList<>();

    public ApplicationUserRole() {
    }

    public ApplicationUserRole(Long id, Role roleEnum) {
        this.id = id;
        this.roleEnum = roleEnum;
    }
}
