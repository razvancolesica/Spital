package com.ibmpractica.spital.repository;

import com.ibmpractica.spital.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,String> {
}
