package com.uniware.hackathonpractice.user.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "confirmation_token")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_user_id")
    private ApplicationUser applicationUser;

    public ConfirmationToken(ApplicationUser user) {
        this.applicationUser = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
