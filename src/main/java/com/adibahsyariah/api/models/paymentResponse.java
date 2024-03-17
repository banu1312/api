package com.adibahsyariah.api.models;

import com.adibahsyariah.api.entity.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class paymentResponse {
    private Payment payment;
    private byte[] imageData;

    public void PaymentWithImage(Payment payment, byte[] imageData) {
        this.payment = payment;
        this.imageData = imageData;
    }

    public Payment getPayment() {
        return payment;
    }

    public byte[] getImageData() {
        return imageData;
    }
}
