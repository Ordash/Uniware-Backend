package com.uniware.hackathonpractice.material.persistence.repository;

import com.uniware.hackathonpractice.material.persistence.model.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
}
