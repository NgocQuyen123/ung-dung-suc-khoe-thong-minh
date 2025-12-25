package com.example.beandroid.controllers;

import com.example.beandroid.DTO.CanNang;
import com.example.beandroid.services.interfaces.ICanNangService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cannang")
public class CanNangController{
    private final ICanNangService service;

    public CanNangController(ICanNangService service) {
        this.service = service;
    }

    // TẠM GIẢ LẬP USER ĐÃ LOGIN
    private int getUserId() {
        return 1;
    }

    @GetMapping("/ngay")
    public CanNang ngay(@RequestParam String date) {
        return service.layTheoNgay(getUserId(), LocalDate.parse(date));
    }

    @GetMapping("/tuan")
    public CanNang tuan(@RequestParam String date) {
        return service.layTheoTuan(getUserId(), LocalDate.parse(date));
    }

    @GetMapping("/thang")
    public CanNang thang(@RequestParam int thang,
                            @RequestParam int nam) {
        return service.layTheoThang(getUserId(), thang, nam);
    }

    @GetMapping("/nam")
    public CanNang nam(@RequestParam int nam) {
        return service.layTheoNam(getUserId(), nam);
    }
}
