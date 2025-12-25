package com.example.beandroid.DTO;

public class CanNangHienTaiRequest {
    private Float canNangHienTai;
    private Boolean luuLichSu = false; // mặc định false
    private Integer idMucDoHoatDong;
    private Integer idNhipDoCanNang;
    private Float canNangMucTieu;

    // getters & setters
    public Float getCanNangHienTai() { return canNangHienTai; }
    public void setCanNangHienTai(Float canNangHienTai) { this.canNangHienTai = canNangHienTai; }

    public Boolean getLuuLichSu() { return luuLichSu; }
    public void setLuuLichSu(Boolean luuLichSu) { this.luuLichSu = luuLichSu; }

    public Integer getIdMucDoHoatDong() { return idMucDoHoatDong; }
    public void setIdMucDoHoatDong(Integer idMucDoHoatDong) { this.idMucDoHoatDong = idMucDoHoatDong; }

    public Integer getIdNhipDoCanNang() { return idNhipDoCanNang; }
    public void setIdNhipDoCanNang(Integer idNhipDoCanNang) { this.idNhipDoCanNang = idNhipDoCanNang; }

    public Float getCanNangMucTieu() { return canNangMucTieu; }
    public void setCanNangMucTieu(Float canNangMucTieu) { this.canNangMucTieu = canNangMucTieu; }
}
