package admin.example.ungdungsuckhoethongminh.model;

public class TocDoCanNang {
    public String id;
    public String ten;
    public double tocDoKgTuan;
    public int caloThayDoiMoiNgay;
    public TocDoCanNang(String id, String ten, double tocDoKgTuan, int caloThayDoiMoiNgay) {
        this.id = id;
        this.ten = ten;
        this.tocDoKgTuan = tocDoKgTuan;
        this.caloThayDoiMoiNgay = caloThayDoiMoiNgay;
    }
}
