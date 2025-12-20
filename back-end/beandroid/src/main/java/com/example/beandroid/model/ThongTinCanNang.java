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
    @JoinColumn(name = "idTKCanNangMT", nullable = false)
    private TaiKhoan taiKhoan;

    @ManyToOne
    @JoinColumn(name = "idMDHD", nullable = false)
    private MucDoHoatDong mucDoHoatDong;

    @ManyToOne
    @JoinColumn(name = "idNDHD", nullable = false)
    private NhipDoCanNang nhipDoCanNang;

    private Float canNangTheoNgay;

    // Với cột DATE
    private LocalDate ngayThietLap;

    // getters & setters
}