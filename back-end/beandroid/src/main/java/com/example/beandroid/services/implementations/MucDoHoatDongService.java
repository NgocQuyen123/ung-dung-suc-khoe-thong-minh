package com.example.beandroid.services.implementations;

import com.example.beandroid.DTO.MucDoHoatDongDTO;
import com.example.beandroid.model.MucDoHoatDong;
import com.example.beandroid.repositories.interfaces.IMucDoHoatDongRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MucDoHoatDongService {
    private final IMucDoHoatDongRepository repository;

    public MucDoHoatDongService(IMucDoHoatDongRepository repository) {
        this.repository = repository;
    }

    public List<MucDoHoatDongDTO> getAll() {
        return repository.findAll().stream()
                .map(entity -> {
                    MucDoHoatDongDTO dto = new MucDoHoatDongDTO();
                    dto.setId(entity.getId());
                    dto.setTenMucDoHoatDong(entity.getTenMDHD()); // chú ý tên entity
                    dto.setMoTa(entity.getMoTa());
                    dto.setCaloHoatDong(entity.getCaloHD());
                    return dto;
                })
                .toList();
    }
}
