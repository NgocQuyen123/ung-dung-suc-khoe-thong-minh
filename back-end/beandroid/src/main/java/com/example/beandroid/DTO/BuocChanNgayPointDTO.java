package com.example.beandroid.DTO;

import java.time.LocalDate;

/**
 * Response DTO cho biểu đồ theo ngày.
 * Format giữ đơn giản để không phá frontend: { "ngay": "YYYY-MM-DD", "soBuoc": number }
 */
public class BuocChanNgayPointDTO {
    private LocalDate ngay;
    private Integer soBuoc;

    public BuocChanNgayPointDTO() {
    }

    public BuocChanNgayPointDTO(LocalDate ngay, Integer soBuoc) {
        this.ngay = ngay;
        this.soBuoc = soBuoc;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
    }

    public Integer getSoBuoc() {
        return soBuoc;
    }

    public void setSoBuoc(Integer soBuoc) {
        this.soBuoc = soBuoc;
    }
}
