package admin.example.ungdungsuckhoethongminh.model;

import com.google.gson.annotations.SerializedName;

public class MucDoHoatDongModel {
    private Integer id;
    @SerializedName("tenMucDoHoatDong")
    private String tenMDHD;
    @SerializedName("moTa")
    private String moTa;

    public Integer getCaloHD() {
        return caloHD;
    }

    public void setCaloHD(Integer caloHD) {
        this.caloHD = caloHD;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenMDHD() {
        return tenMDHD;
    }

    public void setTenMDHD(String tenMDHD) {
        this.tenMDHD = tenMDHD;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("caloHoatDong")
    private Integer caloHD;

    // getter & setter
}
