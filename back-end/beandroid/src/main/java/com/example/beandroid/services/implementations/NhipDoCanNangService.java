package com.example.beandroid.services.implementations;

import com.example.beandroid.model.NhipDoCanNang;
import com.example.beandroid.repositories.interfaces.INhipDoCanNangRepository;

import java.util.List;

public class NhipDoCanNangService {
    private final INhipDoCanNangRepository repository;

    public NhipDoCanNangService(INhipDoCanNangRepository repository) {
        this.repository = repository;
    }

    public List<NhipDoCanNang> getAll() {
        return repository.findAll();
    }
}
