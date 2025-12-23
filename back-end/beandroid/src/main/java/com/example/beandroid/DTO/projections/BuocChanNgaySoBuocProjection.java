package com.example.beandroid.DTO.projections;

import java.time.LocalDate;

/**
 * Projection nhẹ: chỉ lấy Ngày + Số bước, phục vụ vẽ biểu đồ.
 */
public interface BuocChanNgaySoBuocProjection {
    LocalDate getNgay();
    Integer getSoBuoc();
}
