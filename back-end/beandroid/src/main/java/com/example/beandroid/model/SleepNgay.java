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

    // getters & setters
}
