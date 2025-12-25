package com.example.beandroid.services.implementations;

import com.example.beandroid.model.MucDoHoatDong;
import com.example.beandroid.repositories.interfaces.IMucDoHoatDongRepository;

import java.util.List;

public class MucDoHoatDongService {
    private final IMucDoHoatDongRepository repository;

    public MucDoHoatDongService(IMucDoHoatDongRepository repository) {
        this.repository = repository;
    }

    public List<MucDoHoatDong> getAll() {
        return repository.findAll();
    }
}
