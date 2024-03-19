package com.ibmpractica.spital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer id;

    @NotNull
    @Column(name = "pacient_id")
    private String pacientID;
    @NotNull
    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;
    @NotNull
    @Column(name = "specialization")
    private String specialization;
    @NotNull
    @Column(name = "medic")
    private String medic;

}
