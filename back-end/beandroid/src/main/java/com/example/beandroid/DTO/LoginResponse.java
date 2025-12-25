package com.example.beandroid.DTO;

import com.example.beandroid.model.TaiKhoan;

public class LoginResponse {
    private boolean success;
    private String message;
    private TaiKhoan taiKhoan;

    public LoginResponse() {}

    public LoginResponse(boolean success, String message, TaiKhoan taiKhoan) {
        this.success = success;
        this.message = message;
        this.taiKhoan = taiKhoan;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public TaiKhoan getTaiKhoan() { return taiKhoan; }
    public void setTaiKhoan(TaiKhoan taiKhoan) { this.taiKhoan = taiKhoan; }
}
