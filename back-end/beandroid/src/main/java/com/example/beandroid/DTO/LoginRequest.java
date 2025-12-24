package com.example.beandroid.DTO;

public class LoginRequest {
    private String sdt;

    public LoginRequest() {}

    public LoginRequest(String sdt) {
        this.sdt = sdt;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}

