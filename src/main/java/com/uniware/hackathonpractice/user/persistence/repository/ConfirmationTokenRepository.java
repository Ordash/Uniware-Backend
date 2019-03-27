package com.uniware.hackathonpractice.user.persistence.repository;

import com.uniware.hackathonpractice.user.persistence.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}