package com.adibahsyariah.api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class createPaymentReq {
    @NotBlank
    @Size(max = 255)
    private String namaHibah;

    @NotBlank
    @Size(max = 255)
    private int nominal;

    @NotBlank
    private String metode;
     
    @NotNull
    private String reveal;

    @NotNull
    private Long user_id; 

    @Size(max = 700)
    private String pesan; 
}
