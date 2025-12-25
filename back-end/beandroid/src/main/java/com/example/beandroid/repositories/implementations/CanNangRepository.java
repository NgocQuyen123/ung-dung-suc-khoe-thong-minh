package com.example.beandroid.repositories.implementations;

import org.springframework.jdbc.core.JdbcTemplate;
import com.example.beandroid.repositories.interfaces.ICanNangRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class CanNangRepository implements ICanNangRepository{
    private final JdbcTemplate jdbcTemplate;

    public CanNangRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> layCanNangTheoKhoangNgay(
            int userId, LocalDate start, LocalDate end) {

        String sql = """
            SELECT NgayThietLap, CanNangTheoNgay
            FROM ThongTinCanNang
            WHERE idTK = ?
              AND NgayThietLap BETWEEN ? AND ?
            ORDER BY NgayThietLap
        """;

        return jdbcTemplate.queryForList(sql, userId, start, end);
    }

    @Override
    public Double tinhTrungBinhTheoKhoangNgay(
            int userId, LocalDate start, LocalDate end) {

        String sql = """
            SELECT AVG(CanNangTheoNgay)
            FROM ThongTinCanNang
            WHERE idTK = ?
              AND NgayThietLap BETWEEN ? AND ?
        """;

        return jdbcTemplate.queryForObject(sql, Double.class, userId, start, end);
    }

    @Override
    public List<Map<String, Object>> layTrungBinhTheoThang(
            int userId, int nam) {

        String sql = """
            SELECT MONTH(NgayThietLap) AS thang,
                   AVG(CanNangTheoNgay) AS trungBinh
            FROM ThongTinCanNang
            WHERE idTK = ?
              AND YEAR(NgayThietLap) = ?
            GROUP BY MONTH(NgayThietLap)
            ORDER BY thang
        """;

        return jdbcTemplate.queryForList(sql, userId, nam);
    }
}
