package com.adibahsyariah.api.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adibahsyariah.api.entity.Payment;
import com.adibahsyariah.api.models.WebResponse;
import com.adibahsyariah.api.models.createPaymentReq;
import com.adibahsyariah.api.models.paymentResponse;
import com.adibahsyariah.api.models.updatePaymentReq;
import com.adibahsyariah.api.services.paymentService;

@RestController
@RequestMapping("/api/payment/")
@CrossOrigin
public class paymentController {
    @Autowired
    private paymentService paymentService;

    @GetMapping
    public WebResponse<Object> getAllData() throws IOException {
        return WebResponse.<Object>builder().data(paymentService.getAll()).build();
    }
    
    // @GetMapping("{id}")
    // public WebResponse<paymentResponse> getDataById(@PathVariable Long id) throws IOException {
    //     return WebResponse.<paymentResponse>builder().data(paymentService.getById(id)).build();
    // }
    
    @PostMapping
    public WebResponse<Payment> create(@RequestBody createPaymentReq request) {
        
        return WebResponse.<Payment>builder().data(paymentService.create(request)).build();
    }

    @PostMapping("{id}")
    public WebResponse<Optional<Payment>> konfirmasi(@PathVariable Long id ,updatePaymentReq request) {
        return WebResponse.<Optional<Payment>>builder().data(paymentService.konfirmasi(id,request)).build();
    }
    
    @PutMapping("{id}")
    public WebResponse<Optional<Payment>> verifikasi(@PathVariable Long id) {
        return WebResponse.<Optional<Payment>>builder().data(paymentService.verifikasi(id)).build();
    }

    @DeleteMapping("{id}")
    public WebResponse<String> deleteUser(@PathVariable Long id){
        return WebResponse.<String>builder().data(paymentService.deletePayment(id)).build();
    }
    
   
}
