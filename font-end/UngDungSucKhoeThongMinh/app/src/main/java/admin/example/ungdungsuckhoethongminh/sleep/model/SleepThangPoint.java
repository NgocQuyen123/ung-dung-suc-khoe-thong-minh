package admin.example.ungdungsuckhoethongminh.sleep.model;

/**
 * Mirrors backend DTO SleepThangPointDTO.
 */
public class SleepThangPoint {
    public Integer thang; // 1..12

    // totals in minutes
    public Long tongThoiGianNgu;
    public Long tongNguSau;
    public Long tongNguNhe;
    public Long tongNguREM;
    public Long tongThoiGianThuc;
}
