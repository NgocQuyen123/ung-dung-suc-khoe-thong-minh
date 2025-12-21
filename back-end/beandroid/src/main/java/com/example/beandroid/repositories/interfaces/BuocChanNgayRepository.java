package com.example.beandroid.repositories.interfaces;

import com.example.beandroid.DTO.BuocChanTheoNgayDTO;
import com.example.beandroid.DTO.BuocChanTheoThangDTO;
import com.example.beandroid.model.BuocChanNgay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository chỉ đảm nhiệm truy vấn DB.
 * Trả về DTO Projection (interface-based), không trả Entity trực tiếp.
 */
public interface BuocChanNgayRepository extends JpaRepository<BuocChanNgay, Integer> {

    /**
     * Lấy dữ liệu theo khoảng ngày [fromDate, toDate] cho một tài khoản.
     * Kết quả sắp xếp tăng dần theo ngày.
     */
    @Query("""
            select b.Ngay as ngay,
                   b.SoBuoc as soBuoc,
                   b.Kcal as kcal,
                   b.QuangDuong as quangDuong
            from BuocChanNgay b
            where b.taiKhoan.id = :idTaiKhoan
              and b.Ngay between :fromDate and :toDate
            order by b.Ngay asc
            """)
    List<BuocChanTheoNgayDTO> findTheoKhoangNgay(
            @Param("idTaiKhoan") Integer idTaiKhoan,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    /**
     * Lấy dữ liệu tổng hợp theo tháng trong một năm.
     * Trả về 12 tháng (nếu DB không có dữ liệu tháng nào thì tháng đó không xuất hiện).
     */
    @Query("""
            select function('month', b.Ngay) as thang,
                   sum(b.SoBuoc) as tongSoBuoc,
                   sum(b.Kcal) as tongKcal,
                   sum(b.QuangDuong) as tongQuangDuong
            from BuocChanNgay b
            where b.taiKhoan.id = :idTaiKhoan
              and b.Ngay between :fromDate and :toDate
            group by function('month', b.Ngay)
            order by function('month', b.Ngay) asc
            """)
    List<BuocChanTheoThangDTO> tongHopTheoThang(
            @Param("idTaiKhoan") Integer idTaiKhoan,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
