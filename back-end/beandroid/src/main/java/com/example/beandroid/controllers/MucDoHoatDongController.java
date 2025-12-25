package com.example.beandroid.controllers;

import com.example.beandroid.model.MucDoHoatDong;
import com.example.beandroid.services.implementations.MucDoHoatDongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/muc-do-hoat-dong")
public class MucDoHoatDongController {
    private final MucDoHoatDongService service;

    public MucDoHoatDongController(MucDoHoatDongService service) {
        this.service = service;
    }

    @GetMapping
    public List<MucDoHoatDong> getAll() {
        return service.getAll();
    }
}
