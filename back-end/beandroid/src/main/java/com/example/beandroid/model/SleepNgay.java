package com.example.beandroid.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SleepNgay",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idTaiKhoan", "ngay"}))
public class SleepNgay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idTaiKhoan", nullable = false)
    private TaiKhoan taiKhoan;

    private LocalDate ngay;

    private Integer tongThoiGianNgu = 0;
    private Integer thoiGianNguSau = 0;
    private Integer thoiGianNguNhe = 0;
    private Integer thoiGianNguREM = 0;
    private Integer thoiGianThuc = 0;

    private LocalDateTime gioBatDau;
    private LocalDateTime gioKetThuc;

    @Transient
    private Integer nam;

    @Transient
    private Integer thang;

    @Transient
    private Integer tuan;

    @PostLoad
    public void computeDateParts() {
        if (ngay != null) {
            this.nam = ngay.getYear();
            this.thang = ngay.getMonthValue();
            this.tuan = ngay.get( java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR );
        }
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
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

    public LocalDateTime getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(LocalDateTime gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public LocalDateTime getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(LocalDateTime gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Integer getTuan() {
        return tuan;
    }

    public void setTuan(Integer tuan) {
        this.tuan = tuan;
    }
}
