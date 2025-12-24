package com.example.beandroid.DTO.projections;

/**
 * Projection: tổng số bước, quãng đường, thời gian theo tháng.
 */
public interface BuocChanThangSoBuocProjection {
    Integer getThang();
    Long getTongSoBuoc();
    Double getTongKcal();
    Double getTongQuangDuong();
    Long getTongThoiGianGiay();
}
