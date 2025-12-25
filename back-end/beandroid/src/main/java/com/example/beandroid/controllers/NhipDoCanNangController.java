package com.example.beandroid.controllers;

import com.example.beandroid.DTO.NhipDoCanNangDTO;
import com.example.beandroid.model.NhipDoCanNang;
import com.example.beandroid.services.implementations.NhipDoCanNangService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nhip-do-can-nang")

public class NhipDoCanNangController {
    private final NhipDoCanNangService service;

    public NhipDoCanNangController(NhipDoCanNangService service) {
        this.service = service;
    }

    @GetMapping
    public List<NhipDoCanNangDTO> getAll() {
        return service.getAllDTO();
    }
}
