package com.example.beandroid.services.implementations;

import com.example.beandroid.DTO.BuocChanNgayPointDTO;
import com.example.beandroid.DTO.BuocChanThangPointDTO;
import com.example.beandroid.DTO.projections.BuocChanNgaySoBuocProjection;
import com.example.beandroid.DTO.projections.BuocChanThangSoBuocProjection;
import com.example.beandroid.repositories.interfaces.BuocChanNgayRepository;
import com.example.beandroid.services.interfaces.BuocChanService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuocChanServiceImpl implements BuocChanService {

    private final BuocChanNgayRepository buocChanNgayRepository;

    public BuocChanServiceImpl(BuocChanNgayRepository buocChanNgayRepository) {
        this.buocChanNgayRepository = buocChanNgayRepository;
    }

    /* =========================
       API MỚI (REST) - logic thời gian + zero-fill
       ========================= */

    @Override
    public List<BuocChanNgayPointDTO> getDay(Integer idTaiKhoan, LocalDate date) {
        List<BuocChanNgaySoBuocProjection> rows = buocChanNgayRepository.findSoBuocTheoKhoangNgay(idTaiKhoan, date, date);
        int steps = rows.isEmpty() || rows.get(0).getSoBuoc() == null ? 0 : rows.get(0).getSoBuoc();
        return List.of(new BuocChanNgayPointDTO(date, steps));
    }

    @Override
    public List<BuocChanNgayPointDTO> getWeek(Integer idTaiKhoan, LocalDate anyDateInWeek) {
        // Week chuẩn ISO: Thứ 2 -> Chủ nhật
        LocalDate start = anyDateInWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = anyDateInWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return getRangeDaysZeroFill(idTaiKhoan, start, end);
    }

    @Override
    public List<BuocChanNgayPointDTO> getMonth(Integer idTaiKhoan, int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        return getRangeDaysZeroFill(idTaiKhoan, start, end);
    }

    @Override
    public List<BuocChanThangPointDTO> getYear(Integer idTaiKhoan, int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);

        List<BuocChanThangSoBuocProjection> rows = buocChanNgayRepository.tongSoBuocTheoThangTrongNam(idTaiKhoan, start, end);
        Map<Integer, Long> thangToSteps = new HashMap<>();
        for (BuocChanThangSoBuocProjection r : rows) {
            if (r.getThang() != null) {
                thangToSteps.put(r.getThang(), r.getTongSoBuoc() == null ? 0L : r.getTongSoBuoc());
            }
        }

        List<BuocChanThangPointDTO> result = new ArrayList<>(12);
        for (int m = 1; m <= 12; m++) {
            result.add(new BuocChanThangPointDTO(m, thangToSteps.getOrDefault(m, 0L)));
        }
        return result;
    }

    /**
     * Helper: query DB trong khoảng [start, end] rồi zero-fill tất cả ngày trong khoảng.
     */
    private List<BuocChanNgayPointDTO> getRangeDaysZeroFill(Integer idTaiKhoan, LocalDate start, LocalDate end) {
        List<BuocChanNgaySoBuocProjection> rows = buocChanNgayRepository.findSoBuocTheoKhoangNgay(idTaiKhoan, start, end);

        Map<LocalDate, Integer> dateToSteps = new HashMap<>();
        for (BuocChanNgaySoBuocProjection r : rows) {
            if (r.getNgay() != null) {
                dateToSteps.put(r.getNgay(), r.getSoBuoc() == null ? 0 : r.getSoBuoc());
            }
        }

        List<BuocChanNgayPointDTO> result = new ArrayList<>();
        LocalDate d = start;
        while (!d.isAfter(end)) {
            result.add(new BuocChanNgayPointDTO(d, dateToSteps.getOrDefault(d, 0)));
            d = d.plusDays(1);
        }
        return result;
    }
}
