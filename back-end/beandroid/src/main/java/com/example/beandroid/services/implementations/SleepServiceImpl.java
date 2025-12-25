package com.example.beandroid.services.implementations;

import com.example.beandroid.DTO.SleepNgayPointDTO;
import com.example.beandroid.DTO.SleepThangPointDTO;
import com.example.beandroid.DTO.SleepThangAvgDTO;
import com.example.beandroid.model.SleepNgay;
import com.example.beandroid.repositories.interfaces.SleepRepository;
import com.example.beandroid.services.interfaces.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation của SleepService
 * Đồng nhất với BuocChanServiceImpl
 */
@Service
public class SleepServiceImpl implements SleepService {

    private static String formatMinutesToHHmm(Integer minutesFromMidnight) {
        if (minutesFromMidnight == null) return null;
        int m = minutesFromMidnight;
        // normalize
        m = ((m % (24 * 60)) + (24 * 60)) % (24 * 60);
        int h = m / 60;
        int min = m % 60;
        return String.format(java.util.Locale.ROOT, "%02d:%02d", h, min);
    }

    @Autowired
    private SleepRepository sleepRepository;

    /**
     * Lấy dữ liệu giấc ngủ theo ngày
     */
    @Override
    public List<SleepNgayPointDTO> getDay(Integer idTaiKhoan, LocalDate ngay) {
        Optional<SleepNgay> opt = sleepRepository.findByIdTaiKhoanAndNgay(idTaiKhoan, ngay);
        
        if (opt.isPresent()) {
            SleepNgay s = opt.get();
            return List.of(new SleepNgayPointDTO(
                s.getNgay(),
                s.getTongThoiGianNgu(),
                s.getThoiGianNguSau(),
                s.getThoiGianNguNhe(),
                s.getThoiGianNguREM(),
                s.getThoiGianThuc(),
                s.getGioBatDau(),
                s.getGioKetThuc()
            ));
        }
        
        // Zero-fill nếu không có dữ liệu
        return List.of(new SleepNgayPointDTO(ngay, 0, 0, 0, 0, 0, null, null));
    }

    /**
     * Lấy dữ liệu giấc ngủ theo tuần (7 ngày từ thứ 2 đến chủ nhật)
     * Tương tự BuocChanServiceImpl
     */
    @Override
    public List<SleepNgayPointDTO> getWeek(Integer idTaiKhoan, LocalDate ngay) {
        // Tìm ngày thứ 2 của tuần chứa ngày này
        LocalDate monday = ngay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        
        // Lấy dữ liệu từ DB (7 ngày từ thứ 2 đến chủ nhật)
        LocalDate sunday = monday.plusDays(6);
        List<SleepNgay> dataFromDb = sleepRepository.findByIdTaiKhoanAndNgayBetween(
            idTaiKhoan, monday, sunday
        );
        
        // Map để tra cứu nhanh
        Map<LocalDate, SleepNgay> dataMap = dataFromDb.stream()
                .collect(Collectors.toMap(SleepNgay::getNgay, s -> s));
        
        // Tạo list 7 ngày với zero-fill
        List<SleepNgayPointDTO> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = monday.plusDays(i);
            SleepNgay s = dataMap.get(currentDate);
            
            if (s != null) {
                result.add(new SleepNgayPointDTO(
                    s.getNgay(),
                    s.getTongThoiGianNgu(),
                    s.getThoiGianNguSau(),
                    s.getThoiGianNguNhe(),
                    s.getThoiGianNguREM(),
                    s.getThoiGianThuc(),
                    s.getGioBatDau(),
                    s.getGioKetThuc()
                ));
            } else {
                // Zero-fill
                result.add(new SleepNgayPointDTO(currentDate, 0, 0, 0, 0, 0, null, null));
            }
        }
        
        return result;
    }

    /**
     * Lấy dữ liệu giấc ngủ theo tháng
     * Tương tự BuocChanServiceImpl
     */
    @Override
    public List<SleepNgayPointDTO> getMonth(Integer idTaiKhoan, Integer nam, Integer thang) {
        // Lấy dữ liệu từ database
        List<SleepNgay> dataFromDb = sleepRepository.findByIdTaiKhoanAndThang(idTaiKhoan, thang, nam);
        
        // Map để tra cứu nhanh
        Map<LocalDate, SleepNgay> dataMap = dataFromDb.stream()
                .collect(Collectors.toMap(SleepNgay::getNgay, s -> s));
        
        // Số ngày trong tháng
        LocalDate firstDay = LocalDate.of(nam, thang, 1);
        int daysInMonth = firstDay.lengthOfMonth();
        
        // Tạo list đầy đủ các ngày với zero-fill
        List<SleepNgayPointDTO> result = new ArrayList<>();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = LocalDate.of(nam, thang, day);
            SleepNgay s = dataMap.get(currentDate);
            
            if (s != null) {
                result.add(new SleepNgayPointDTO(
                    s.getNgay(),
                    s.getTongThoiGianNgu(),
                    s.getThoiGianNguSau(),
                    s.getThoiGianNguNhe(),
                    s.getThoiGianNguREM(),
                    s.getThoiGianThuc(),
                    s.getGioBatDau(),
                    s.getGioKetThuc()
                ));
            } else {
                // Zero-fill
                result.add(new SleepNgayPointDTO(currentDate, 0, 0, 0, 0, 0, null, null));
            }
        }
        
        return result;
    }

    /**
     * Lấy dữ liệu giấc ngủ theo năm (tổng hợp theo tháng)
     * Tương tự BuocChanServiceImpl
     */
    @Override
    public List<SleepThangAvgDTO> getYearAvg(Integer idTaiKhoan, Integer nam) {
        List<Object[]> rows = sleepRepository.avgByMonth(idTaiKhoan, nam);

        Map<Integer, SleepThangAvgDTO> map = new HashMap<>();
        for (Object[] row : rows) {
            Integer thang = row[0] != null ? ((Number) row[0]).intValue() : null;
            Double avgSleep = row[1] != null ? ((Number) row[1]).doubleValue() : null;
            Double avgBed = row[2] != null ? ((Number) row[2]).doubleValue() : null;
            Double avgWake = row[3] != null ? ((Number) row[3]).doubleValue() : null;
            if (thang != null) {
                map.put(thang, new SleepThangAvgDTO(thang, avgSleep, avgBed, avgWake));
            }
        }

        // zero-fill đủ 12 tháng
        List<SleepThangAvgDTO> result = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            SleepThangAvgDTO dto = map.get(m);
            if (dto == null) {
                dto = new SleepThangAvgDTO(m, 0, 0, 0);
                dto.setHasData(false);
                // No data => keep times null
                dto.setAvgBedTime(null);
                dto.setAvgWakeTime(null);
            } else {
                dto.setHasData(true);
                dto.setAvgBedTime(formatMinutesToHHmm(dto.getAvgBedMinutes()));
                dto.setAvgWakeTime(formatMinutesToHHmm(dto.getAvgWakeMinutes()));
            }
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<SleepThangPointDTO> getYear(Integer idTaiKhoan, Integer nam) {
        // Lấy dữ liệu tổng hợp từ database
        List<Object[]> dataFromDb = sleepRepository.findByIdTaiKhoanAndNam(idTaiKhoan, nam);
        
        // Chuyển đổi Object[] sang SleepThangPointDTO
        Map<Integer, SleepThangPointDTO> dataMap = new HashMap<>();
        for (Object[] row : dataFromDb) {
            Integer thang = ((Number) row[0]).intValue();
            Long tongThoiGianNgu = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            Long tongNguSau = row[2] != null ? ((Number) row[2]).longValue() : 0L;
            Long tongNguNhe = row[3] != null ? ((Number) row[3]).longValue() : 0L;
            Long tongNguREM = row[4] != null ? ((Number) row[4]).longValue() : 0L;
            Long tongThoiGianThuc = row[5] != null ? ((Number) row[5]).longValue() : 0L;
            
            dataMap.put(thang, new SleepThangPointDTO(thang, tongThoiGianNgu, tongNguSau, 
                    tongNguNhe, tongNguREM, tongThoiGianThuc));
        }
        
        // Zero-fill: tạo đầy đủ 12 tháng
        List<SleepThangPointDTO> result = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            if (dataMap.containsKey(month)) {
                result.add(dataMap.get(month));
            } else {
                // Tháng không có dữ liệu: trả về 0
                result.add(new SleepThangPointDTO(month, 0L, 0L, 0L, 0L, 0L));
            }
        }
        
        return result;
    }
}
