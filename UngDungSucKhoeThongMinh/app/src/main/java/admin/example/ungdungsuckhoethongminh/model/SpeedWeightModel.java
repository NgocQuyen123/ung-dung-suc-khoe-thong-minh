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
}
