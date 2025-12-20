package com.example.beandroid.services.implementations;

import com.example.beandroid.model.TaiKhoan;
import com.example.beandroid.repositories.interfaces.ITaiKhoanRepository;
import com.example.beandroid.services.interfaces.ITaiKhoanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaiKhoanService implements ITaiKhoanService {
    private final ITaiKhoanRepository taiKhoanRepository;

    public TaiKhoanService(ITaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Override
    public List<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanRepository.findAll();
    }
}
