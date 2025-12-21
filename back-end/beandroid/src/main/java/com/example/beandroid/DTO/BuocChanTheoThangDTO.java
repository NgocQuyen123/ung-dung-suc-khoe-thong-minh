package com.example.beandroid.DTO;

/**
 * Projection DTO trả về dữ liệu tổng hợp theo tháng.
 */
public interface BuocChanTheoThangDTO {
    Integer getThang();

    Long getTongSoBuoc();

    Double getTongKcal();

    Double getTongQuangDuong();
}
