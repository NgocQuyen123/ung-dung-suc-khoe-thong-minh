package com.example.beandroid.services.interfaces;


import com.example.beandroid.DTO.CanNangHienTaiRequest;
import com.example.beandroid.DTO.CanNangHienTaiResponse;
import com.example.beandroid.model.TaiKhoan;
import java.util.List;

public interface ITaiKhoanService {
    List<TaiKhoan> getAllTaiKhoan();
    CanNangHienTaiResponse layCanNangHienTai(Integer idTaiKhoan);

    CanNangHienTaiResponse capNhatCanNangHienTai(Integer idTaiKhoan, CanNangHienTaiRequest request);
}