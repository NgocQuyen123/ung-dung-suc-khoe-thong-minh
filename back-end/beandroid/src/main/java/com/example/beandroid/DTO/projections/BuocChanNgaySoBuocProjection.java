package com.example.beandroid.DTO.projections;

import java.time.LocalDate;

/**
 * Projection: lấy Ngày + Số bước + Quãng đường + Thời gian, phục vụ vẽ biểu đồ.
 */
public interface BuocChanNgaySoBuocProjection {
    LocalDate getNgay();
    Integer getSoBuoc();
    Float getKcal();
    Float getQuangDuong();
    Integer getThoiGianGiay();
}
