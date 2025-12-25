package admin.example.ungdungsuckhoethongminh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CanNangThangModel {
    public String label;
    public Double trungBinh;

    // DATA CHO BIỂU ĐỒ
    @SerializedName("tuan")
    public List<CanNangTuanTrongThangModel> danhSachTuan;
}
