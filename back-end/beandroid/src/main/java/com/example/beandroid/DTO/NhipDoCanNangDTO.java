package com.example.beandroid.DTO;

public class NhipDoCanNangDTO {
    private Integer id;
    private String tenNDCD;
    private Double tocDoKgTuan;
    private Integer caloThayDoiMoiNgay;

    public NhipDoCanNangDTO(Integer id, String tenNDCD, Double tocDoKgTuan, Integer caloThayDoiMoiNgay) {
        this.id = id;
        this.tenNDCD = tenNDCD;
        this.tocDoKgTuan = tocDoKgTuan;
        this.caloThayDoiMoiNgay = caloThayDoiMoiNgay;
    }

    // getters & setters
    public Integer getId() { return id; }
    public String getTenNDCD() { return tenNDCD; }
    public Double getTocDoKgTuan() { return tocDoKgTuan; }
    public Integer getCaloThayDoiMoiNgay() { return caloThayDoiMoiNgay; }
}
