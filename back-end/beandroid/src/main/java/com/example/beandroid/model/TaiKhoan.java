package com.example.beandroid.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "Sdt", length = 15, nullable = false)
    private String sdt;

    @Column(name = "TenTK", length = 100, nullable = false)
    private String tenTK;

    @Column(name = "GioiTinh", length = 10)
    private String gioiTinh;

    @Column(name = "ChieuCao")
    private Integer chieuCao;

    @Column(name = "NamSinh")
    private Integer namSinh;

    @Column(name = "CanNang")
    private Float canNang;

    @OneToMany(mappedBy = "taiKhoan")
    @JsonIgnore
    private List<BuocChanNgay> buocChanNgays;

    @OneToMany(mappedBy = "taiKhoan")
    @JsonIgnore
    private List<SleepNgay> sleepNgays;

    @OneToMany(mappedBy = "taiKhoan")
    @JsonIgnore
    private List<ThongTinCanNang> thongTinCanNangs;

    /* ================= GETTERS & SETTERS ================= */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Integer getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(Integer chieuCao) {
        this.chieuCao = chieuCao;
    }

    public Integer getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(Integer namSinh) {
        this.namSinh = namSinh;
    }

    public Float getCanNang() {
        return canNang;
    }

    public void setCanNang(Float canNang) {
        this.canNang = canNang;
    }
}
