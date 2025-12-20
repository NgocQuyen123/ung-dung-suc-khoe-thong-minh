package com.example.beandroid.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "MucDoHoatDong")
public class MucDoHoatDong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TenMDHD", length = 100, nullable = false)
    private String tenMDHD;

    @Column(name = "MoTa", length = 255)
    private String moTa;

    private Integer caloHD;

    @OneToMany(mappedBy = "mucDoHoatDong")
    private List<ThongTinCanNang> thongTinCanNangs;

    // getters & setters
}