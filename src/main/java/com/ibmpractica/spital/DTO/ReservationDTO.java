package com.ibmpractica.spital.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Pacient ID cannot be null")
    private Integer pacientID;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @NotNull(message = "Reservation date cannot be null")
    private LocalDateTime reservationDate;
    @NotBlank(message = "Specialization cannot be blank")
    private String specialization;
    @NotBlank(message = "Medic cannot be blank")
    private String medic;
}
