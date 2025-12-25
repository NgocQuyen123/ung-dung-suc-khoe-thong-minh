package admin.example.ungdungsuckhoethongminh.model;

public class SpeedWeightModel {
    public String id;
    public String ten;
    public double tocDoKgTuan;
    public int caloThayDoiMoiNgay;
    public SpeedWeightModel(String id, String ten, double tocDoKgTuan, int caloThayDoiMoiNgay) {
        this.id = id;
        this.ten = ten;
        this.tocDoKgTuan = tocDoKgTuan;
        this.caloThayDoiMoiNgay = caloThayDoiMoiNgay;
    }
    // Getter & Setter cho id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    // Getter & Setter cho ten
    public String getTen() {
        return ten;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }

    // Getter & Setter cho tocDoKgTuan
    public double getTocDoKgTuan() {
        return tocDoKgTuan;
    }
    public void setTocDoKgTuan(double tocDoKgTuan) {
        this.tocDoKgTuan = tocDoKgTuan;
    }

    // Getter & Setter cho caloThayDoiMoiNgay
    public int getCaloThayDoiMoiNgay() {
        return caloThayDoiMoiNgay;
    }
    public void setCaloThayDoiMoiNgay(int caloThayDoiMoiNgay) {
        this.caloThayDoiMoiNgay = caloThayDoiMoiNgay;
    }
}
