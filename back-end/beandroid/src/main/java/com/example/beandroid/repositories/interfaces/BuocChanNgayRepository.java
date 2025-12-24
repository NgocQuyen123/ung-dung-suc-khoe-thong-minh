package com.example.beandroid.repositories.interfaces;

import com.example.beandroid.DTO.projections.BuocChanNgaySoBuocProjection;
import com.example.beandroid.DTO.projections.BuocChanThangSoBuocProjection;
import com.example.beandroid.model.BuocChanNgay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository chỉ đảm nhiệm truy vấn DB.
 * Chỉ giữ các query cần cho API mới.
 */
public interface BuocChanNgayRepository extends JpaRepository<BuocChanNgay, Integer> {

    /**
     * Query: lấy Ngày + Số bước + Quãng đường + Thời gian trong khoảng.
     * Service sẽ zero-fill các ngày bị thiếu.
     */
    @Query("""
            select b.Ngay as ngay,
                   b.SoBuoc as soBuoc,
                   b.QuangDuong as quangDuong,
                   b.ThoiGianGiay as thoiGianGiay
            from BuocChanNgay b
            where b.taiKhoan.id = :idTaiKhoan
              and b.Ngay between :fromDate and :toDate
            order by b.Ngay asc
            """)
    List<BuocChanNgaySoBuocProjection> findSoBuocTheoKhoangNgay(
            @Param("idTaiKhoan") Integer idTaiKhoan,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    /**
     * Tổng hợp số bước, quãng đường, thời gian theo tháng trong một năm cụ thể.
     */
    @Query("""
            select function('month', b.Ngay) as thang,
                   sum(b.SoBuoc) as tongSoBuoc,
                   sum(b.QuangDuong) as tongQuangDuong,
                   sum(b.ThoiGianGiay) as tongThoiGianGiay
            from BuocChanNgay b
            where b.taiKhoan.id = :idTaiKhoan
              and b.Ngay between :fromDate and :toDate
            group by function('month', b.Ngay)
            order by function('month', b.Ngay) asc
            """)
    List<BuocChanThangSoBuocProjection> tongSoBuocTheoThangTrongNam(
            @Param("idTaiKhoan") Integer idTaiKhoan,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
