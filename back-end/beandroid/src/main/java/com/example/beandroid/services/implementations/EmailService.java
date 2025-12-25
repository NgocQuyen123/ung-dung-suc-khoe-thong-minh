package com.example.beandroid.services.implementations;

import com.example.beandroid.services.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;


@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendOtp(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Xác thực email");
        message.setText(
                "Mã OTP của bạn là: " + otp +
                        "\nOTP có hiệu lực trong 5 phút."
        );

        mailSender.send(message);
    }
}
