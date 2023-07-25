package com.ibmpractica.spital.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacientDTO {

    private String firstName;
    private String lastName;
    private int age;
    private String issue;
    private String pacientID;

}
