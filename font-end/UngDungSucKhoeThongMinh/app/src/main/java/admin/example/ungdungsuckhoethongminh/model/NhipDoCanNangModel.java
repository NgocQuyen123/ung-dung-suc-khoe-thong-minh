package admin.example.ungdungsuckhoethongminh.model;

public class NhipDoCanNangModel {
    private Integer id;
    private String tenNDCD;
    private Double tocDoKgTuan;
    private Integer caloThayDoiMoiNgay;

    // getter & setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTenNDCD() { return tenNDCD; }
    public void setTenNDCD(String tenNDCD) { this.tenNDCD = tenNDCD; }
    public Double getTocDoKgTuan() { return tocDoKgTuan; }
    public void setTocDoKgTuan(Double tocDoKgTuan) { this.tocDoKgTuan = tocDoKgTuan; }
    public Integer getCaloThayDoiMoiNgay() { return caloThayDoiMoiNgay; }
    public void setCaloThayDoiMoiNgay(Integer caloThayDoiMoiNgay) { this.caloThayDoiMoiNgay = caloThayDoiMoiNgay; }
}
