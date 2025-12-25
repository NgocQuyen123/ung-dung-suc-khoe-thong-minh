package com.example.beandroid.services.interfaces;

import com.example.beandroid.DTO.TaiKhoanDTO;
import com.example.beandroid.model.TaiKhoan;

import java.util.List;

public interface ITaiKhoanService {

    List<TaiKhoan> getAllTaiKhoan();

    TaiKhoan getTaiKhoanById(Integer id);

    TaiKhoan updateTaiKhoan(TaiKhoanDTO taiKhoanDTO);
}
