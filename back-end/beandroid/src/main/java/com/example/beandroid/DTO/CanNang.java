package com.example.beandroid.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CanNang {
    // Dùng chung
    public String label;
    public Double trungBinh;

    // Tuần
    public LocalDate startDate;
    public LocalDate endDate;
    public List<NgayCanNang> ngay;

    // Tháng
    public Integer thang;
    public Integer nam;
    public List<TuanCanNang> tuan;

    // Năm
    public List<ThangCanNang> thangTrongNam;

    // ========= INNER DTO =========
    public static class NgayCanNang {
        public LocalDate ngay;
        public Double canNang;
    }

    public static class TuanCanNang {
        public String label;
        public Double trungBinhTuan;
    }

    public static class ThangCanNang {
        public Integer thang;
        public Double trungBinhThang;
    }
}
