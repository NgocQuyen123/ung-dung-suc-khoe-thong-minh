package com.example.beandroid.services.interfaces;

import com.example.beandroid.DTO.CanNang;

import java.time.LocalDate;

public interface ICanNangService {
    CanNang layTheoNgay(int userId, LocalDate ngay);

    CanNang layTheoTuan(int userId, LocalDate ngayBatKy);

    CanNang layTheoThang(int userId, int thang, int nam);

    CanNang layTheoNam(int userId, int nam);
}
