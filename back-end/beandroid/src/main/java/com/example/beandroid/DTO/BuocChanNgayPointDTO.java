package com.example.beandroid.DTO;

import java.time.LocalDate;

/**
 * Response DTO cho biểu đồ theo ngày.
 * Format giữ đơn giản để không phá frontend: { "date": "YYYY-MM-DD", "soBuoc": number }
 */
public class BuocChanNgayPointDTO {
    private LocalDate date;
    private Integer soBuoc;

    public BuocChanNgayPointDTO() {
    }

    public BuocChanNgayPointDTO(LocalDate date, Integer soBuoc) {
        this.date = date;
        this.soBuoc = soBuoc;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSoBuoc() {
        return soBuoc;
    }

    public void setSoBuoc(Integer soBuoc) {
        this.soBuoc = soBuoc;
    }
}
