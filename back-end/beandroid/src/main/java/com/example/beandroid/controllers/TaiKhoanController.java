package com.example.beandroid.controllers;

import com.example.beandroid.DTO.CreateTaiKhoanRequest;
import com.example.beandroid.DTO.LoginRequest;
import com.example.beandroid.DTO.LoginResponse;
import com.example.beandroid.model.TaiKhoan;
import com.example.beandroid.services.interfaces.ITaiKhoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taikhoan")
public class TaiKhoanController {

    private final ITaiKhoanService taiKhoanService;

    public TaiKhoanController(ITaiKhoanService taiKhoanService) {
        this.taiKhoanService = taiKhoanService;
    }

    // API TEST KẾT NỐI DATABASE
    @GetMapping
    public List<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanService.getAllTaiKhoan();
    }

    @PostMapping
    public TaiKhoan create(@RequestBody CreateTaiKhoanRequest request) {
        return taiKhoanService.create(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String sdt = request.getSdt();
        return taiKhoanService.loginBySdt(sdt);
    }

}
