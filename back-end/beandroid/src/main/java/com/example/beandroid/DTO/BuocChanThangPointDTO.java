package com.example.beandroid.DTO;

/**
 * Response DTO cho biểu đồ theo tháng (tổng bước chân, quãng đường, thời gian mỗi tháng).
 * Format: { "thang": 1..12, "soBuoc": number, "quangDuong": number, "thoiGianGiay": number }
 */
public class BuocChanThangPointDTO {
    private Integer thang;
    private Long soBuoc;
    private Double kcal;
    private Double quangDuong; // Tổng quãng đường (km) trong tháng
    private Long thoiGianGiay; // Tổng thời gian (giây) trong tháng

    public BuocChanThangPointDTO() {
    }

    public BuocChanThangPointDTO(Integer thang, Long soBuoc, Double kcal, Double quangDuong, Long thoiGianGiay) {
        this.thang = thang;
        this.soBuoc = soBuoc;
        this.kcal = kcal;
        this.quangDuong = quangDuong;
        this.thoiGianGiay = thoiGianGiay;
    }

    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Long getSoBuoc() {
        return soBuoc;
    }

    public void setSoBuoc(Long soBuoc) {
        this.soBuoc = soBuoc;
    }

    public Double getKcal() {
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    public Double getQuangDuong() {
        return quangDuong;
    }

    public void setQuangDuong(Double quangDuong) {
        this.quangDuong = quangDuong;
    }

    public Long getThoiGianGiay() {
        return thoiGianGiay;
    }

    public void setThoiGianGiay(Long thoiGianGiay) {
        this.thoiGianGiay = thoiGianGiay;
    }
}
