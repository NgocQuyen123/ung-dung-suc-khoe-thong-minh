package admin.example.ungdungsuckhoethongminh.DTO;

public class CreateTaiKhoanRequest {

    private String sdt ;
    private String tenTK;
    private String gioiTinh;
    private Integer chieuCao;
    private Integer namSinh;
    private Float canNang;

    // getters & setters


    public CreateTaiKhoanRequest(String sdt, String tenTK, String gioiTinh, Integer chieuCao, Integer namSinh, Float canNang) {
        this.sdt = sdt;
        this.tenTK = tenTK;
        this.gioiTinh = gioiTinh;
        this.chieuCao = chieuCao;
        this.namSinh = namSinh;
        this.canNang = canNang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        sdt = sdt;
    }

    public String getTenTK() {
        return tenTK;
    }
    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Integer getChieuCao() {
        return chieuCao;
    }
    public void setChieuCao(Integer chieuCao) {
        this.chieuCao = chieuCao;
    }

    public Integer getNamSinh() {
        return namSinh;
    }
    public void setNamSinh(Integer namSinh) {
        this.namSinh = namSinh;
    }

    public Float getCanNang() {
        return canNang;
    }
    public void setCanNang(Float canNang) {
        this.canNang = canNang;
    }
}