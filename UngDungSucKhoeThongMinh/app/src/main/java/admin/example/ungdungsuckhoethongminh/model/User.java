package admin.example.ungdungsuckhoethongminh.model;

public class User {
    private int id;
    private String ten;
    private String sdt;

    private String gioiTinh;
    private int chieuCao;
    private int namSinh;

    // Constructor trống cần thiết cho Gson/Retrofit
    public User() { }

    // Constructor đầy đủ
    public User(int id, String ten, String sdt,  String gioiTinh, int chieuCao, int namSinh) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.chieuCao = chieuCao;
        this.namSinh = namSinh;
    }

    // Getter
    public int getId() { return id; }
    public String getTen() { return ten; }
    public String getSdt() { return sdt; }

    public String getGioiTinh() { return gioiTinh; }
    public int getChieuCao() { return chieuCao; }
    public int getNamSinh() { return namSinh; }

    // Setter
    public void setId(int id) { this.id = id; }
    public void setTen(String ten) { this.ten = ten; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    public void setChieuCao(int chieuCao) { this.chieuCao = chieuCao; }
    public void setNamSinh(int namSinh) { this.namSinh = namSinh; }
}
