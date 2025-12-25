package com.example.beandroid.DTO;

/**
 * Response DTO cho biểu đồ giấc ngủ theo tháng
 * Đồng nhất format với BuocChanThangPointDTO
 * Tổng hợp các chỉ số giấc ngủ trong 1 tháng
 */
public class SleepThangPointDTO {
    private Integer thang;
    private Long tongThoiGianNgu;
    private Long tongNguSau;
    private Long tongNguNhe;
    private Long tongNguREM;
    private Long tongThoiGianThuc;

    // Constructor
    public SleepThangPointDTO() {
    }

    public SleepThangPointDTO(Integer thang, Long tongThoiGianNgu, Long tongNguSau, 
                              Long tongNguNhe, Long tongNguREM, Long tongThoiGianThuc) {
        this.thang = thang;
        this.tongThoiGianNgu = tongThoiGianNgu != null ? tongThoiGianNgu : 0L;
        this.tongNguSau = tongNguSau != null ? tongNguSau : 0L;
        this.tongNguNhe = tongNguNhe != null ? tongNguNhe : 0L;
        this.tongNguREM = tongNguREM != null ? tongNguREM : 0L;
        this.tongThoiGianThuc = tongThoiGianThuc != null ? tongThoiGianThuc : 0L;
    }

    // Getters and Setters
    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Long getTongThoiGianNgu() {
        return tongThoiGianNgu;
    }

    public void setTongThoiGianNgu(Long tongThoiGianNgu) {
        this.tongThoiGianNgu = tongThoiGianNgu;
    }

    public Long getTongNguSau() {
        return tongNguSau;
    }

    public void setTongNguSau(Long tongNguSau) {
        this.tongNguSau = tongNguSau;
    }

    public Long getTongNguNhe() {
        return tongNguNhe;
    }

    public void setTongNguNhe(Long tongNguNhe) {
        this.tongNguNhe = tongNguNhe;
    }

    public Long getTongNguREM() {
        return tongNguREM;
    }

    public void setTongNguREM(Long tongNguREM) {
        this.tongNguREM = tongNguREM;
    }

    public Long getTongThoiGianThuc() {
        return tongThoiGianThuc;
    }

    public void setTongThoiGianThuc(Long tongThoiGianThuc) {
        this.tongThoiGianThuc = tongThoiGianThuc;
    }
}
