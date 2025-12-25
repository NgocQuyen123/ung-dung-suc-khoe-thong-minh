package com.example.beandroid.services.interfaces;

import com.example.beandroid.DTO.TaiKhoanDTO ;
import com.example.beandroid.DTO.CreateTaiKhoanRequest;
import com.example.beandroid.DTO.LoginResponse;
import com.example.beandroid.DTO.CanNangHienTaiRequest;
import com.example.beandroid.DTO.CanNangHienTaiResponse;
import com.example.beandroid.model.TaiKhoan;
import java.util.List;


public interface ITaiKhoanService {
    List<TaiKhoan> getAllTaiKhoan();
    TaiKhoan create(CreateTaiKhoanRequest request);
    LoginResponse loginBySdt(String sdt);
    TaiKhoan getTaiKhoanById(Integer id);

    TaiKhoan updateTaiKhoan(TaiKhoanDTO taiKhoanDTO);
    CanNangHienTaiResponse layCanNangHienTai(Integer idTaiKhoan);

    CanNangHienTaiResponse capNhatCanNangHienTai(Integer idTaiKhoan, CanNangHienTaiRequest request);
}