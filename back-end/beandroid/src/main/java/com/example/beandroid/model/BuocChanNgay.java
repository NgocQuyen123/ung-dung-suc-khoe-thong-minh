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

    @PostLoad
    public void computeDateParts() {
        if (Ngay != null) {
            this.nam = Ngay.getYear();
            this.thang = Ngay.getMonthValue();
            this.tuan = Ngay.get( java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR );
        }
    }
}