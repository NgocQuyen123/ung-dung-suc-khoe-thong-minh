package com.example.beandroid.DTO.projections;

/**
 * Projection nhẹ: tổng số bước theo tháng.
 */
public interface BuocChanThangSoBuocProjection {
    Integer getThang();
    Long getTongSoBuoc();
}
