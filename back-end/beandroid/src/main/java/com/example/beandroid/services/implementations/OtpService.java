package com.example.beandroid.services.implementations;

import com.example.beandroid.services.interfaces.IOtpService;
import org.springframework.stereotype.Service;
import com.example.beandroid.DTO.LoginResponse;
import com.example.beandroid.DTO.LoginRequest;
import com.example.beandroid.Util.OtpGenerator;
import com.example.beandroid.Util.HmacUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


@Service
public class OtpService implements IOtpService {
    @Value("${otp.secret}")
    private String secret;

    @Autowired
    private EmailService emailService;

//    @Override
//    public LoginResponse sendOtp(String email) {
//
//        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
//            throw new IllegalArgumentException("Email không hợp lệ");
//        }
//
//        String otp = OtpGenerator.generate();
//        long expiredAt = System.currentTimeMillis() + 5 * 60 * 1000;
//
//        String data = email + otp + expiredAt;
//        String signature = HmacUtil.hmacSha256(data, secret);
//
//        emailService.sendOtp(email, otp);
//
//        return new LoginResponse(expiredAt, signature);
//    }
//
//    @Override
//    public boolean verifyOtp(LoginRequest request) {
//
//        if (System.currentTimeMillis() > request.getExpiredAt()) {
//            return false;
//        }
//
//        String data = request.getEmail()
//                + request.getOtp()
//                + request.getExpiredAt();
//
//        String expected =
//                HmacUtil.hmacSha256(data, secret);
//
//        return expected.equals(request.getSignature());
//    }
}
