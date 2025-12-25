package com.example.beandroid.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ThongTinCanNang")
public class ThongTinCanNang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idTK", nullable = false)
    private TaiKhoan taiKhoan;

    @Column(name = "CanNangMucTieu")
    private Float canNangMucTieu;

    @ManyToOne
    @JoinColumn(name = "idMDHD", nullable = false)
    private MucDoHoatDong mucDoHoatDong;

    @ManyToOne
    @JoinColumn(name = "idNDHD", nullable = false)
    private NhipDoCanNang nhipDoCanNang;

    @Column(name = "CanNangTheoNgay")
    private Float canNangTheoNgay;

    // Với cột DATE
    @Column(name = "NgayThietLap")
    private LocalDate ngayThietLap;

    // getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public TaiKhoan getTaiKhoan() { return taiKhoan; }
    public void setTaiKhoan(TaiKhoan taiKhoan) { this.taiKhoan = taiKhoan; }

    public MucDoHoatDong getMucDoHoatDong() { return mucDoHoatDong; }
    public void setMucDoHoatDong(MucDoHoatDong mucDoHoatDong) { this.mucDoHoatDong = mucDoHoatDong; }

    public NhipDoCanNang getNhipDoCanNang() { return nhipDoCanNang; }
    public void setNhipDoCanNang(NhipDoCanNang nhipDoCanNang) { this.nhipDoCanNang = nhipDoCanNang; }

    public Float getCanNangTheoNgay() { return canNangTheoNgay; }
    public void setCanNangTheoNgay(Float canNangTheoNgay) { this.canNangTheoNgay = canNangTheoNgay; }

    public LocalDate getNgayThietLap() { return ngayThietLap; }
    public void setNgayThietLap(LocalDate ngayThietLap) { this.ngayThietLap = ngayThietLap; }
    public Float getCanNangMucTieu() {
        return canNangMucTieu;
    }

    public void setCanNangMucTieu(Float canNangMucTieu) {
        this.canNangMucTieu = canNangMucTieu;
    }

}