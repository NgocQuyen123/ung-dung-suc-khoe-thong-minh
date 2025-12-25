package com.example.beandroid.services.implementations;

import com.example.beandroid.DTO.NhipDoCanNangDTO;
import com.example.beandroid.model.NhipDoCanNang;
import com.example.beandroid.repositories.interfaces.INhipDoCanNangRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhipDoCanNangService {
    private final INhipDoCanNangRepository repository;

    public NhipDoCanNangService(INhipDoCanNangRepository repository) {
        this.repository = repository;
    }

    public List<NhipDoCanNangDTO> getAllDTO() {
        return repository.findAll()
                .stream()
                .map(e -> new NhipDoCanNangDTO(
                        e.getId(),
                        e.getTenNDCD(),
                        e.getTocDoKgTuan(),
                        e.getCaloThayDoiMoiNgay()
                ))
                .collect(Collectors.toList());
    }
}
