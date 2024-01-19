package com.adibahsyariah.api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class createUserRequest {
    
    @NotBlank
    @Size(max = 255)
    private String email; 

    @NotBlank
    @Size(max = 255)
    private String password; 
    @NotBlank
    private String name;
     
    @NotNull
    private LocalDate birthDay; 

    @NotNull
    private Long role_id; 


    @NotBlank
    @Size(max = 255)
    private String phone; 
}
