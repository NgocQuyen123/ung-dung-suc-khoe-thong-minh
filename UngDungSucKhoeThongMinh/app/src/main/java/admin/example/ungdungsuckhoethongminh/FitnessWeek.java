package admin.example.ungdungsuckhoethongminh;

import java.util.List;

public class FitnessWeek {
    public String tuan;
    public int thang;
    public String ngay_bat_dau;
    public String ngay_ket_thuc;

    public int tong_buoc;
    public int trung_binh;

    public List<ChiTiet> chi_tiet;

    public NoiBat noi_bat;

    public static class ChiTiet {
        public String thu;
        public String ngay;
        public int so_buoc;
        public int calo;
        public int quang_duong_m;
        public int thoi_gian_phut;
    }

    public static class NoiBat {
        public int so_buoc;
        public int calo;
        public int quang_duong_m;
        public int thoi_gian_phut;
    }
}
