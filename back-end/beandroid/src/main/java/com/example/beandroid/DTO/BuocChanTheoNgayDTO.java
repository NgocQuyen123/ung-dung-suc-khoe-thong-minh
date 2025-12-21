package com.example.beandroid.DTO;

import java.time.LocalDate;

/**
 * Projection DTO trả về dữ liệu bước chân theo ngày.
 * Không trả Entity trực tiếp.
 */
public interface BuocChanTheoNgayDTO {
    LocalDate getNgay();

    Integer getSoBuoc();

    Float getKcal();

    Float getQuangDuong();
}
