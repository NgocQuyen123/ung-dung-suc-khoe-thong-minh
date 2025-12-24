package com.example.beandroid.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "BuocChanNgay",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idTaiKhoan", "Ngay"}))
public class BuocChanNgay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idTaiKhoan", nullable = false)
    private TaiKhoan taiKhoan;

    private LocalDate Ngay;
    private Integer SoBuoc = 0;
    private Float Kcal = 0f;
    private Float QuangDuong = 0f;
    private Integer ThoiGianGiay = 0;

    @Transient
    private Integer nam;

    @Transient
    private Integer thang;

    @Transient
    private Integer tuan;

    // getters & setters

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
        return Ngay;
    }

    public void setNgay(LocalDate ngay) {
        Ngay = ngay;
    }

    public Integer getSoBuoc() {
        return SoBuoc;
    }

    public void setSoBuoc(Integer soBuoc) {
        SoBuoc = soBuoc;
    }

    public Float getKcal() {
        return Kcal;
    }

    public void setKcal(Float kcal) {
        Kcal = kcal;
    }

    public Float getQuangDuong() {
        return QuangDuong;
    }

    public void setQuangDuong(Float quangDuong) {
        QuangDuong = quangDuong;
    }

    public Integer getThoiGianGiay() {
        return ThoiGianGiay;
    }

    public void setThoiGianGiay(Integer thoiGianGiay) {
        ThoiGianGiay = thoiGianGiay;
    }

    public Integer getNam() {
        return nam;
    }

    public Integer getThang() {
        return thang;
    }

    public Integer getTuan() {
        return tuan;
    }

    @PostLoad
    public void computeDateParts() {
        if (Ngay != null) {
            this.nam = Ngay.getYear();
            this.thang = Ngay.getMonthValue();
            this.tuan = Ngay.get( java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR );
        }
    }
}