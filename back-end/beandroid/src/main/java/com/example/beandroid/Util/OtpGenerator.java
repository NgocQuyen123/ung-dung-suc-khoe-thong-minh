package com.example.beandroid.Util;

import java.security.SecureRandom;

public class OtpGenerator {

    public static String generate() {
        return String.valueOf(
                new SecureRandom().nextInt(900000) + 100000
        );
    }
}

