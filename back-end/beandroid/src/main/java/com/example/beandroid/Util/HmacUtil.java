package com.example.beandroid.Util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HmacUtil {

    public static String hmacSha256(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key =
                    new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(key);

            byte[] raw = mac.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(raw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
