package com.adibahsyariah.api.models;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class updateUser {
    @Size(max = 255)
    private String email; 

    @Size(max = 255)
    private String password; 

    @Size(max = 255)
    private String name;
     
    private LocalDate birthDay; 

    private Long role_id; 

    @Size(max = 255)
    private String phone; 

}
