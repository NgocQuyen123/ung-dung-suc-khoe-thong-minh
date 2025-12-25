package admin.example.ungdungsuckhoethongminh.model;

import com.google.gson.annotations.SerializedName;

public class CanNangHienTaiResponse {
    private Integer idTaiKhoan;
    @SerializedName("canNangHienTai") // phải match key trả về từ BE
    private double canNangHienTai;

    public double getCanNangHienTai() {
        return canNangHienTai;
    }

    public void setCanNangHienTai(double canNangHienTai) {
        this.canNangHienTai = canNangHienTai;
    }
}
