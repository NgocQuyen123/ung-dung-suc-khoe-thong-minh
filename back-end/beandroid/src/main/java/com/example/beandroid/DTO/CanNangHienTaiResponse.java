package com.example.beandroid.DTO;

public class CanNangHienTaiResponse {
    private Integer idTaiKhoan;
    private Float canNangHienTai;

    public CanNangHienTaiResponse() {}
    public CanNangHienTaiResponse(Integer idTaiKhoan, Float canNangHienTai) {
        this.idTaiKhoan = idTaiKhoan;
        this.canNangHienTai = canNangHienTai;
    }

    public Integer getIdTaiKhoan() { return idTaiKhoan; }
    public void setIdTaiKhoan(Integer idTaiKhoan) { this.idTaiKhoan = idTaiKhoan; }

    public Float getCanNangHienTai() { return canNangHienTai; }
    public void setCanNangHienTai(Float canNangHienTai) { this.canNangHienTai = canNangHienTai; }
}
