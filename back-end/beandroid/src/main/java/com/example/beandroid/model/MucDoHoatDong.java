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

    public String getTenMDHD() {
        return tenMDHD;
    }

    public void setTenMDHD(String tenMDHD) {
        this.tenMDHD = tenMDHD;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getCaloHD() {
        return caloHD;
    }

    public void setCaloHD(Integer caloHD) {
        this.caloHD = caloHD;
    }

    public List<ThongTinCanNang> getThongTinCanNangs() {
        return thongTinCanNangs;
    }

    public void setThongTinCanNangs(List<ThongTinCanNang> thongTinCanNangs) {
        this.thongTinCanNangs = thongTinCanNangs;
    }

    private Integer caloHD;

    @OneToMany(mappedBy = "mucDoHoatDong")
    private List<ThongTinCanNang> thongTinCanNangs;

    // getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

}