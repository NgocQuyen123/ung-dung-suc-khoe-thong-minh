package com.example.beandroid.repositories.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ICanNangRepository {
    List<Map<String, Object>> layCanNangTheoKhoangNgay(
            int userId, LocalDate start, LocalDate end);

    Double tinhTrungBinhTheoKhoangNgay(
            int userId, LocalDate start, LocalDate end);

    List<Map<String, Object>> layTrungBinhTheoThang(
            int userId, int nam);
}
