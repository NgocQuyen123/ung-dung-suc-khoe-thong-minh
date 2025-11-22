package admin.example.ungdungsuckhoethongminh.model;

public class SpeedWeight {
    public String id;
    public String ten;
    public double tocDoKgTuan;
    public int caloThayDoiMoiNgay;
    public SpeedWeight(String id, String ten, double tocDoKgTuan, int caloThayDoiMoiNgay) {
        this.id = id;
        this.ten = ten;
        this.tocDoKgTuan = tocDoKgTuan;
        this.caloThayDoiMoiNgay = caloThayDoiMoiNgay;
    }
}
