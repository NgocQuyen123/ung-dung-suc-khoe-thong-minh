package com.example.beandroid.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Response DTO cho biểu đồ giấc ngủ theo ngày
 * Đồng nhất format với BuocChanNgayPointDTO
 */
public class SleepNgayPointDTO {
    // Android (Gson) không parse tốt java.time.* nếu không custom adapter.
    // Vì vậy response dùng String (tương tự module Steps).
    private String ngay;                 // yyyy-MM-dd
    private Integer tongThoiGianNgu;     // phút
    private Integer thoiGianNguSau;      // phút
    private Integer thoiGianNguNhe;      // phút
    private Integer thoiGianNguREM;      // phút
    private Integer thoiGianThuc;        // phút
    private String gioBatDau;            // H:mm (vd: 1:13)
    private String gioKetThuc;           // H:mm (vd: 6:11)

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("H:mm");

    public SleepNgayPointDTO() {
    }

    public SleepNgayPointDTO(LocalDate ngay, Integer tongThoiGianNgu, Integer thoiGianNguSau,
                             Integer thoiGianNguNhe, Integer thoiGianNguREM, Integer thoiGianThuc,
                             LocalDateTime gioBatDau, LocalDateTime gioKetThuc) {
        this.ngay = ngay != null ? DATE_FMT.format(ngay) : null;
        this.tongThoiGianNgu = tongThoiGianNgu != null ? tongThoiGianNgu : 0;
        this.thoiGianNguSau = thoiGianNguSau != null ? thoiGianNguSau : 0;
        this.thoiGianNguNhe = thoiGianNguNhe != null ? thoiGianNguNhe : 0;
        this.thoiGianNguREM = thoiGianNguREM != null ? thoiGianNguREM : 0;
        this.thoiGianThuc = thoiGianThuc != null ? thoiGianThuc : 0;
        this.gioBatDau = gioBatDau != null ? TIME_FMT.format(gioBatDau) : null;
        this.gioKetThuc = gioKetThuc != null ? TIME_FMT.format(gioKetThuc) : null;
    }

    // Getters and Setters
    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public Integer getTongThoiGianNgu() {
        return tongThoiGianNgu;
    }

    public void setTongThoiGianNgu(Integer tongThoiGianNgu) {
        this.tongThoiGianNgu = tongThoiGianNgu;
    }

    public Integer getThoiGianNguSau() {
        return thoiGianNguSau;
    }

    public void setThoiGianNguSau(Integer thoiGianNguSau) {
        this.thoiGianNguSau = thoiGianNguSau;
    }

    public Integer getThoiGianNguNhe() {
        return thoiGianNguNhe;
    }

    public void setThoiGianNguNhe(Integer thoiGianNguNhe) {
        this.thoiGianNguNhe = thoiGianNguNhe;
    }

    public Integer getThoiGianNguREM() {
        return thoiGianNguREM;
    }

    public void setThoiGianNguREM(Integer thoiGianNguREM) {
        this.thoiGianNguREM = thoiGianNguREM;
    }

    public Integer getThoiGianThuc() {
        return thoiGianThuc;
    }

    public void setThoiGianThuc(Integer thoiGianThuc) {
        this.thoiGianThuc = thoiGianThuc;
    }

    public String getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public String getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(String gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }
}
