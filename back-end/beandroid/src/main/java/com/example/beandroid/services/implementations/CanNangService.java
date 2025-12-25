package com.example.beandroid.services.implementations;

import com.example.beandroid.DTO.CanNang;
import com.example.beandroid.repositories.interfaces.ICanNangRepository;
import com.example.beandroid.services.interfaces.ICanNangService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class CanNangService implements ICanNangService {
    private final ICanNangRepository repo;

    public CanNangService(ICanNangRepository repo) {
        this.repo = repo;
    }

    @Override
    public CanNang layTheoNgay(int userId, LocalDate ngay) {
        CanNang dto = new CanNang();
        dto.label = ngay.toString();

        // lấy dữ liệu trong ngày (có thể rỗng)
        List<Map<String, Object>> data = repo.layCanNangTheoKhoangNgay(userId, ngay, ngay);
        if (data.isEmpty()) {
            dto.trungBinh = null;
        } else {
            Object v = data.get(0).get("CanNangTheoNgay");
            dto.trungBinh = (v == null) ? null : ((Number) v).doubleValue();
        }
        return dto;
    }

    @Override
    public CanNang layTheoTuan(int userId, LocalDate ngayBatKy) {

        LocalDate start = ngayBatKy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = start.plusDays(6);

        CanNang dto = new CanNang();
        dto.startDate = start;
        dto.endDate = end;
        dto.label = buildWeekLabel(start, end);

        // Lấy tất cả bản ghi có tồn tại trong tuần (DB trả chỉ những ngày có dữ liệu)
        List<Map<String, Object>> data = repo.layCanNangTheoKhoangNgay(userId, start, end);

        // map ngày->value (an toàn với sql.Date)
        Map<LocalDate, Double> map = new HashMap<>();
        for (Map<String, Object> row : data) {
            Object dateObj = row.get("NgayThietLap");
            LocalDate d = null;
            if (dateObj instanceof java.sql.Date) {
                d = ((java.sql.Date) dateObj).toLocalDate();
            } else if (dateObj instanceof java.time.LocalDate) {
                d = (java.time.LocalDate) dateObj;
            }
            Object val = row.get("CanNangTheoNgay");
            if (d != null && val != null) {
                map.put(d, ((Number) val).doubleValue());
            }
        }

        dto.ngay = new ArrayList<>();
        double sum = 0;
        int cnt = 0;
        for (int i = 0; i < 7; i++) {
            LocalDate d = start.plusDays(i);
            CanNang.NgayCanNang n = new CanNang.NgayCanNang();
            n.ngay = d;
            n.canNang = map.get(d); // null nếu không có data
            if (n.canNang != null) { sum += n.canNang; cnt++; }
            dto.ngay.add(n);
        }
        dto.trungBinh = (cnt == 0) ? null : (sum / cnt);

        return dto;
    }

    @Override
    public CanNang layTheoThang(int userId, int thang, int nam) {

        LocalDate firstOfMonth = LocalDate.of(nam, thang, 1);
        LocalDate lastOfMonth = firstOfMonth.withDayOfMonth(firstOfMonth.lengthOfMonth());

        CanNang dto = new CanNang();
        dto.label = "Tháng " + thang + " năm " + nam;

        // Lấy tất cả dữ liệu của tháng (dùng 1 query)
        List<Map<String, Object>> data = repo.layCanNangTheoKhoangNgay(userId, firstOfMonth, lastOfMonth);

        // map ngày->value để dễ lookup
        Map<LocalDate, Double> dayMap = new HashMap<>();
        for (Map<String, Object> row : data) {
            Object dateObj = row.get("NgayThietLap");
            LocalDate d = null;
            if (dateObj instanceof java.sql.Date) {
                d = ((java.sql.Date) dateObj).toLocalDate();
            } else if (dateObj instanceof java.time.LocalDate) {
                d = (java.time.LocalDate) dateObj;
            }
            Object val = row.get("CanNangTheoNgay");
            if (d != null && val != null) {
                dayMap.put(d, ((Number) val).doubleValue());
            }
        }

        // Build weeks overlapping the month:
        List<CanNang.TuanCanNang> weeks = new ArrayList<>();
        // start cursor at Monday on/ before firstOfMonth
        LocalDate cursor = firstOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        while (!cursor.isAfter(lastOfMonth)) {
            LocalDate wStart = cursor;
            LocalDate wEnd = cursor.plusDays(6);
            // overlap with month
            LocalDate overlapStart = wStart.isBefore(firstOfMonth) ? firstOfMonth : wStart;
            LocalDate overlapEnd = wEnd.isAfter(lastOfMonth) ? lastOfMonth : wEnd;

            // compute avg for this overlap by iterating days and using dayMap
            double s = 0;
            int c = 0;
            List<CanNang.NgayCanNang> weekDays = new ArrayList<>();
            LocalDate it = overlapStart;
            while (!it.isAfter(overlapEnd)) {
                CanNang.NgayCanNang n = new CanNang.NgayCanNang();
                n.ngay = it;
                n.canNang = dayMap.get(it);
                if (n.canNang != null) { s += n.canNang; c++; }
                weekDays.add(n);
                it = it.plusDays(1);
            }
            CanNang.TuanCanNang w = new CanNang.TuanCanNang();
            w.label = buildWeekLabel(overlapStart, overlapEnd);
            w.trungBinhTuan = (c == 0) ? null : (s / c);
            // (optionally) attach weekDays if you extended DTO
            // w.days = weekDays;

            weeks.add(w);

            cursor = cursor.plusWeeks(1);
        }

        // set month-level average: compute from dayMap
        double sAll = 0;
        int cAll = 0;
        for (Double v : dayMap.values()) { sAll += v; cAll++; }
        dto.trungBinh = (cAll == 0) ? null : (sAll / cAll);
        dto.tuan = weeks; // set week list in dto

        return dto;
    }

    @Override
    public CanNang layTheoNam(int userId, int nam) {

        CanNang dto = new CanNang();
        dto.label = "Năm " + nam;
        dto.thangTrongNam = new ArrayList<>();

        for (int m = 1; m <= 12; m++) {
            LocalDate first = LocalDate.of(nam, m, 1);
            LocalDate last = first.withDayOfMonth(first.lengthOfMonth());

            // get all data for the month
            List<Map<String, Object>> data = repo.layCanNangTheoKhoangNgay(userId, first, last);

            // compute month avg and also compute week breakdown similar to above
            Map<LocalDate, Double> dayMap = new HashMap<>();
            for (Map<String, Object> row : data) {
                Object dateObj = row.get("NgayThietLap");
                LocalDate d = null;
                if (dateObj instanceof java.sql.Date) {
                    d = ((java.sql.Date) dateObj).toLocalDate();
                } else if (dateObj instanceof java.time.LocalDate) {
                    d = (java.time.LocalDate) dateObj;
                }
                Object val = row.get("CanNangTheoNgay");
                if (d != null && val != null) dayMap.put(d, ((Number) val).doubleValue());
            }

            double s = 0; int c = 0;
            for (Double v : dayMap.values()) { s += v; c++; }
            CanNang.ThangCanNang t = new CanNang.ThangCanNang();
            t.thang = m;
            t.trungBinhThang = (c == 0) ? null : (s / c);

            // build weeks for this month if you want week breakdown in year response
            List<CanNang.TuanCanNang> weeks = new ArrayList<>();
            LocalDate cursor = first.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate lastOfMonth = last;
            while (!cursor.isAfter(lastOfMonth)) {
                LocalDate wStart = cursor;
                LocalDate wEnd = cursor.plusDays(6);
                LocalDate overlapStart = wStart.isBefore(first) ? first : wStart;
                LocalDate overlapEnd = wEnd.isAfter(lastOfMonth) ? lastOfMonth : wEnd;

                double sw = 0; int cw = 0;
                LocalDate it = overlapStart;
                while (!it.isAfter(overlapEnd)) {
                    Double val = dayMap.get(it);
                    if (val != null) { sw += val; cw++; }
                    it = it.plusDays(1);
                }
                CanNang.TuanCanNang w = new CanNang.TuanCanNang();
                w.label = buildWeekLabel(overlapStart, overlapEnd);
                w.trungBinhTuan = (cw == 0) ? null : (sw / cw);
                weeks.add(w);

                cursor = cursor.plusWeeks(1);
            }

            // optionally attach weeks to t if DTO supports it
            // t.tuan = weeks;

            dto.thangTrongNam.add(t);
        }

        return dto;
    }

    // helper: label builder
    private String buildWeekLabel(LocalDate s, LocalDate e) {
        if (s.getMonthValue() == e.getMonthValue()) {
            return "Thg " + s.getMonthValue() + " " + s.getDayOfMonth() + "-" + e.getDayOfMonth();
        } else {
            return s.getDayOfMonth() + " thg " + s.getMonthValue() + " - " + e.getDayOfMonth() + " thg " + e.getMonthValue();
        }
    }
}
