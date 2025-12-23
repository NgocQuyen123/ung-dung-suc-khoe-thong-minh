package com.example.beandroid.DTO;

/**
 * Response DTO cho biểu đồ theo tháng (tổng bước chân mỗi tháng).
 * Format: { "thang": 1..12, "soBuoc": number }
 */
public class BuocChanThangPointDTO {
    private Integer thang;
    private Long soBuoc;

    public BuocChanThangPointDTO() {
    }

    public BuocChanThangPointDTO(Integer thang, Long soBuoc) {
        this.thang = thang;
        this.soBuoc = soBuoc;
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
}
