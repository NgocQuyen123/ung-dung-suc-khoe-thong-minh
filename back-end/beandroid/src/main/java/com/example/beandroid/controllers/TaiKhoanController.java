package com.example.beandroid.controllers;

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
}
