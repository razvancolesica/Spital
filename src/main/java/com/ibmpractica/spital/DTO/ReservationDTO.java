package com.ibmpractica.spital.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Integer id;
    private String pacientID;
    private LocalDateTime reservationDate;
    private String specialization;
    private String medic;
}
