package admin.example.ungdungsuckhoethongminh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CanNangTuanModel {
    public String label;
    public Double trungBinh;
    public String ngayBatDau;
    public String ngayKetThuc;

    // DATA CHO BIỂU ĐỒ
    @SerializedName("ngay")
    public List<NgayCanNangModel> danhSachNgay;
}
