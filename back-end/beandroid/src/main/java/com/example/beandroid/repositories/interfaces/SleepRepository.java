package com.example.beandroid.repositories.interfaces;

import com.example.beandroid.model.SleepNgay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho SleepNgay với Native Query SQL Server
 */
@Repository
public interface SleepRepository extends JpaRepository<SleepNgay, Integer> {

    /**
     * Lấy dữ liệu giấc ngủ theo ngày
     */
    @Query(value = """
            SELECT * FROM SleepNgay 
            WHERE idTaiKhoan = :idTaiKhoan 
            AND ngay = :ngay
            """, nativeQuery = true)
    Optional<SleepNgay> findByIdTaiKhoanAndNgay(
            @Param("idTaiKhoan") Integer idTaiKhoan,
            @Param("ngay") LocalDate ngay);

    /**
     * Lấy dữ liệu giấc ngủ trong khoảng thời gian (dùng cho tuần)
     */
    @Query(value = """
            SELECT * FROM SleepNgay 
            WHERE idTaiKhoan = :idTaiKhoan 
            AND ngay BETWEEN :startDate AND :endDate
            ORDER BY ngay ASC
            """, nativeQuery = true)
    List<SleepNgay> findByIdTaiKhoanAndNgayBetween(
            @Param("idTaiKhoan") Integer idTaiKhoan,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Lấy dữ liệu giấc ngủ theo tháng
     */
    @Query(value = """
            SELECT * FROM SleepNgay 
            WHERE idTaiKhoan = :idTaiKhoan 
            AND YEAR(ngay) = :year
            AND MONTH(ngay) = :month
            ORDER BY ngay ASC
            """, nativeQuery = true)
    List<SleepNgay> findByIdTaiKhoanAndThang(
            @Param("idTaiKhoan") Integer idTaiKhoan,
            @Param("month") Integer month,
            @Param("year") Integer year);

    /**
     * Lấy dữ liệu giấc ngủ theo năm (GROUP BY tháng)
     * Trả về tổng các chỉ số theo từng tháng
     */
    @Query(value = """
            SELECT 
                MONTH(ngay) as thang,
                ISNULL(SUM(tongThoiGianNgu), 0) as tongThoiGianNgu,
                ISNULL(SUM(thoiGianNguSau), 0) as tongNguSau,
                ISNULL(SUM(thoiGianNguNhe), 0) as tongNguNhe,
                ISNULL(SUM(thoiGianNguREM), 0) as tongNguREM,
                ISNULL(SUM(thoiGianThuc), 0) as tongThoiGianThuc
            FROM SleepNgay 
            WHERE idTaiKhoan = :idTaiKhoan 
            AND YEAR(ngay) = :year
            GROUP BY MONTH(ngay)
            ORDER BY MONTH(ngay) ASC
            """, nativeQuery = true)
    List<Object[]> findByIdTaiKhoanAndNam(
            @Param("idTaiKhoan") Integer idTaiKhoan,
            @Param("year") Integer year);

        @Query(value = """
                SELECT
                MONTH(ngay) AS thang,
                AVG(DATEDIFF(MINUTE, gioBatDau, gioKetThuc)) AS avgSleepMinutes,
                AVG(DATEPART(HOUR, gioBatDau) * 60 + DATEPART(MINUTE, gioBatDau)) AS avgBedMinutes,
                AVG(DATEPART(HOUR, gioKetThuc) * 60 + DATEPART(MINUTE, gioKetThuc)) AS avgWakeMinutes
                FROM SleepNgay
                WHERE idTaiKhoan = :idTaiKhoan
                AND YEAR(ngay) = :nam
                AND gioBatDau IS NOT NULL
                AND gioKetThuc IS NOT NULL
                GROUP BY MONTH(ngay)
                ORDER BY MONTH(ngay)
                """, nativeQuery = true)
        List<Object[]> avgByMonth(
                @Param("idTaiKhoan") Integer idTaiKhoan,
                @Param("nam") Integer nam
        );
}
