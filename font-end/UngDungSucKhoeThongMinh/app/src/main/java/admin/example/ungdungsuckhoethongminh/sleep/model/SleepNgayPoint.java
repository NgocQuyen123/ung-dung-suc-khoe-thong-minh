package admin.example.ungdungsuckhoethongminh.sleep.model;

/**
 * Mirrors backend DTO SleepNgayPointDTO.
 */
public class SleepNgayPoint {
    public String ngay; // yyyy-MM-dd

    // minutes
    public Integer tongThoiGianNgu;
    public Integer thoiGianNguSau;
    public Integer thoiGianNguNhe;
    public Integer thoiGianNguREM;
    public Integer thoiGianThuc;

    // H:mm (can be null)
    public String gioBatDau;
    public String gioKetThuc;
}
