package admin.example.ungdungsuckhoethongminh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CanNangNamModel {
    public String label;
    public Double trungBinh;

    // DATA CHO BIỂU ĐỒ
    @SerializedName("thangTrongNam")
    public List<CanNangThangTrongNamModel> danhSachThang;
}
