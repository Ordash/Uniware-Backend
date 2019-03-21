package com.uniware.hackathonpractice.user.persistence.repository;

import com.uniware.hackathonpractice.user.persistence.model.ApplicationUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<ApplicationUserRole, Long> {

    Optional<ApplicationUserRole> findById(Long id);
}
