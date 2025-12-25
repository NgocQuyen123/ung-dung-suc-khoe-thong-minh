package com.example.beandroid.controllers;

import com.example.beandroid.DTO.LoginResponse;
import com.example.beandroid.DTO.SendOtpRequest;
import com.example.beandroid.DTO.LoginRequest;
import com.example.beandroid.services.implementations.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private OtpService otpService;

//    @PostMapping("/send-otp")
//    public LoginResponse sendOtp(@RequestBody SendOtpRequest request) {
//        return otpService.sendOtp(request.getEmail());
//    }

//    @PostMapping("/verify-otp")
//    public ResponseEntity<?> verifyOtp(
//            @RequestBody LoginRequest request) {
//
//        boolean valid = otpService.verifyOtp(request);
//        return ResponseEntity.ok(valid);
//    }
}

