package admin.example.ungdungsuckhoethongminh.model;

import java.util.List;

public class Fitness {

    private String ngay;
    private int so_buoc;
    private double quang_duong_m;
    private double calo;
    private List<Activity> hoat_dong;

    public String getNgay() {
        return ngay;
    }

    public int getSo_buoc() {
        return so_buoc;
    }

    public double getQuang_duong_m() {
        return quang_duong_m;
    }

    public double getCalo() {
        return calo;
    }

    public List<Activity> getHoat_dong() {
        return hoat_dong;
    }

    public static class Activity {
        private String loai;
        private int thoi_gian_phut;

        public String getLoai() {
            return loai;
        }

        public int getThoi_gian_phut() {
            return thoi_gian_phut;
        }
    }
}