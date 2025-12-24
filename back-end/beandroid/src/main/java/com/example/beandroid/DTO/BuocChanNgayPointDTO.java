package com.example.beandroid.DTO;

import java.time.LocalDate;

/**
 * Response DTO cho biểu đồ theo ngày.
 * Bao gồm: ngày, số bước, quãng đường (km), thời gian (giây)
 */
public class BuocChanNgayPointDTO {
    private LocalDate ngay;
    private Integer soBuoc;
    private Float kcal;
    private Float quangDuong; // km
    private Integer thoiGianGiay; // giây

    public BuocChanNgayPointDTO() {
    }

    public BuocChanNgayPointDTO(LocalDate ngay, Integer soBuoc, Float kcal, Float quangDuong, Integer thoiGianGiay) {
        this.ngay = ngay;
        this.soBuoc = soBuoc;
        this.kcal = kcal;
        this.quangDuong = quangDuong;
        this.thoiGianGiay = thoiGianGiay;
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

    public Float getKcal() {
        return kcal;
    }

    public void setKcal(Float kcal) {
        this.kcal = kcal;
    }

    public Float getQuangDuong() {
        return quangDuong;
    }

    public void setQuangDuong(Float quangDuong) {
        this.quangDuong = quangDuong;
    }

    public Integer getThoiGianGiay() {
        return thoiGianGiay;
    }

    public void setThoiGianGiay(Integer thoiGianGiay) {
        this.thoiGianGiay = thoiGianGiay;
    }
}
