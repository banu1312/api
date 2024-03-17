package com.adibahsyariah.api.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.adibahsyariah.api.entity.Payment;
import com.adibahsyariah.api.helpers.imageUtils;
import com.adibahsyariah.api.models.createPaymentReq;
import com.adibahsyariah.api.models.paymentResponse;
import com.adibahsyariah.api.models.updatePaymentReq;
import com.adibahsyariah.api.repository.paymentRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class paymentService {
    @Autowired
    private paymentRepository paymentRepository;
    
    private final String FOLDER = "C:/XI HOMEWORK/Web/Donasi/Image/Bukti-Tf/";

    @Transactional
    public List<paymentResponse> getAll() throws IOException{
        List<Payment> payments = paymentRepository.findAll();
        List<paymentResponse> paymentsWithImages = new ArrayList<>();

    for (Payment payment : payments) {
         String filePath = payment.getFilePath();

        // Add null check to handle cases where filePath is null
        if (filePath != null) {
            try {
                byte[] images = Files.readAllBytes(new File(filePath).toPath());
                paymentResponse paymentWithImage = new paymentResponse(payment, images);
                paymentsWithImages.add(paymentWithImage);
            } catch (IOException e) {
                // Handle IOException if there is an issue reading the file
                // You can log the error or take appropriate action
                e.printStackTrace();  // Example: print the stack trace
            }
        }else{
            paymentResponse paymentWithImage = new paymentResponse(payment, null);
            paymentsWithImages.add(paymentWithImage);   
        }
    }

    return paymentsWithImages;
    }

    @Transactional
    public paymentResponse getById(Long id) throws IOException{
        if(!paymentRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Not Found");
        }
        Optional<Payment> payments = paymentRepository.findById(id);
        Payment payment = payments.get();
        if(payment.getFilePath() != null){
            byte[] images = Files.readAllBytes(new File(payment.getFilePath()).toPath());
            paymentResponse paymentResponse = new paymentResponse(payment,images);
            return paymentResponse;
        }
        paymentResponse paymentResponse = new paymentResponse(payment,null);
        return paymentResponse;
    }
    
    @Transactional
    public Payment create(createPaymentReq request){
        try{
            Payment payment = new Payment();
            payment.setNamaHibah(request.getNamaHibah());
            payment.setNominal(request.getNominal());
            payment.setPesan(request.getPesan());
            payment.setMetode(request.getMetode());
            payment.setStatus("Belum Bayar");
            payment.setUser_id(request.getUser_id());
            payment.setReveal(request.getReveal());
            paymentRepository.save(payment);

            return payment;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    @Transactional
    public Optional<Payment> konfirmasi(Long id, updatePaymentReq request){
        try{
            String filePath = FOLDER + request.getFile().getOriginalFilename();
            Optional<Payment> payments = paymentRepository.findById(id);
            Payment payment = payments.get();
            payment.setFilePath(filePath);
            payment.setStatus("Sudah Dibayar");
            request.getFile().transferTo(new File(filePath));
            paymentRepository.save(payment);
            
            return payments;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    @Transactional
    public Optional<Payment> verifikasi(Long id){
        try{
            Optional<Payment> payments = paymentRepository.findById(id);
            Payment payment = payments.get();
            payment.setStatus("Diverifikasi");
            paymentRepository.save(payment);
            
            return payments;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    @Transactional
    public byte[] getImage(String filePath) throws IOException{
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    @Transactional
    public String deletePayment(Long id){
        try {
            Optional<Payment> payments = paymentRepository.findById(id);
            Payment payment = payments.get();
            Files.deleteIfExists(new File(payment.getFilePath()).toPath());
            paymentRepository.deleteById(id);
            return "Success";
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting image: " + e.getMessage());
        }
    }
}
