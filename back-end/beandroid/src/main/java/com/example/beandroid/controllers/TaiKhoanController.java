package com.example.beandroid.controllers;

import com.example.beandroid.DTO.CreateTaiKhoanRequest;
import com.example.beandroid.DTO.LoginRequest;
import com.example.beandroid.DTO.LoginResponse;
import com.example.beandroid.model.TaiKhoan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.beandroid.DTO.TaiKhoanDTO;
import com.example.beandroid.services.interfaces.ITaiKhoanService;

@RestController
@RequestMapping("/api/taikhoan")
public class TaiKhoanController {

    private final ITaiKhoanService service;
    private static final Logger logger = LoggerFactory.getLogger(TaiKhoanController.class);

    public TaiKhoanController(ITaiKhoanService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<TaiKhoan> getAll() {
        return service.getAllTaiKhoan();
    }

    @GetMapping("/{id}")
    public TaiKhoan getById(@PathVariable Integer id) {
        return service.getTaiKhoanById(id);
    }

    @PutMapping("/")
    public TaiKhoan update(@RequestBody TaiKhoanDTO taiKhoanDTO) {
        return service.updateTaiKhoan(taiKhoanDTO);
    }

    @PostMapping
    public TaiKhoan create(@RequestBody CreateTaiKhoanRequest request) {
        return service.create(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String sdt = request.getSdt();

        // Log số điện thoại (sdt) khi người dùng gọi API login
        logger.info("Đang cố gắng đăng nhập với số điện thoại: {}", sdt);

        // Gọi dịch vụ đăng nhập
        return service.loginBySdt(sdt);
    }
}
