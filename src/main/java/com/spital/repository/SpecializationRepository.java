package com.spital.repository;

import com.spital.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, String> {

    Optional<Specialization> findBySpecializationName(String specializationName);
}
