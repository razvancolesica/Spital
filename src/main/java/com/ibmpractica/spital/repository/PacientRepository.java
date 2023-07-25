package com.ibmpractica.spital.repository;

import com.ibmpractica.spital.entity.Pacient;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PacientRepository extends JpaRepository<Pacient,String> {

}
