package admin.example.ungdungsuckhoethongminh.model;

public class OtpResponse {
    private String message;
    private String otp; // nếu bạn trả OTP trực tiếp, còn nếu không thì chỉ cần message

    public String getMessage() {
        return message;
    }

    public String getOtp() {
        return otp;
    }
}

