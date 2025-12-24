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
    public List<BuocChanNgayPointDTO> getDay(Integer idTaiKhoan, LocalDate ngay) {
        List<BuocChanNgaySoBuocProjection> rows = buocChanNgayRepository.findSoBuocTheoKhoangNgay(idTaiKhoan, ngay, ngay);
        if (rows.isEmpty()) {
            return List.of(new BuocChanNgayPointDTO(ngay, 0, 0f, 0));
        }
        BuocChanNgaySoBuocProjection r = rows.get(0);
        return List.of(new BuocChanNgayPointDTO(
                ngay,
                r.getSoBuoc() == null ? 0 : r.getSoBuoc(),
                r.getQuangDuong() == null ? 0f : r.getQuangDuong(),
                r.getThoiGianGiay() == null ? 0 : r.getThoiGianGiay()
        ));
    }

    @Override
    public List<BuocChanNgayPointDTO> getWeek(Integer idTaiKhoan, LocalDate ngayBatKyTrongTuan) {
        // Week chuẩn ISO: Thứ 2 -> Chủ nhật
        LocalDate start = ngayBatKyTrongTuan.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = ngayBatKyTrongTuan.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return getRangeDaysZeroFill(idTaiKhoan, start, end);
    }

    @Override
    public List<BuocChanNgayPointDTO> getMonth(Integer idTaiKhoan, int nam, int thang) {
        YearMonth ym = YearMonth.of(nam, thang);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        return getRangeDaysZeroFill(idTaiKhoan, start, end);
    }

    @Override
    public List<BuocChanThangPointDTO> getYear(Integer idTaiKhoan, int nam) {
        LocalDate start = LocalDate.of(nam, 1, 1);
        LocalDate end = LocalDate.of(nam, 12, 31);

        List<BuocChanThangSoBuocProjection> rows = buocChanNgayRepository.tongSoBuocTheoThangTrongNam(idTaiKhoan, start, end);
        
        // Map để lưu dữ liệu theo tháng
        Map<Integer, BuocChanThangSoBuocProjection> thangToData = new HashMap<>();
        for (BuocChanThangSoBuocProjection r : rows) {
            if (r.getThang() != null) {
                thangToData.put(r.getThang(), r);
            }
        }

        // Zero-fill tất cả 12 tháng
        List<BuocChanThangPointDTO> result = new ArrayList<>(12);
        for (int m = 1; m <= 12; m++) {
            BuocChanThangSoBuocProjection data = thangToData.get(m);
            if (data != null) {
                result.add(new BuocChanThangPointDTO(
                        m,
                        data.getTongSoBuoc() == null ? 0L : data.getTongSoBuoc(),
                        data.getTongQuangDuong() == null ? 0.0 : data.getTongQuangDuong(),
                        data.getTongThoiGianGiay() == null ? 0L : data.getTongThoiGianGiay()
                ));
            } else {
                // Tháng không có dữ liệu - zero fill
                result.add(new BuocChanThangPointDTO(m, 0L, 0.0, 0L));
            }
        }
        return result;
    }

    /**
     * Helper: query DB trong khoảng [start, end] rồi zero-fill tất cả ngày trong khoảng.
     */
    private List<BuocChanNgayPointDTO> getRangeDaysZeroFill(Integer idTaiKhoan, LocalDate start, LocalDate end) {
        List<BuocChanNgaySoBuocProjection> rows = buocChanNgayRepository.findSoBuocTheoKhoangNgay(idTaiKhoan, start, end);

        // Map để lưu dữ liệu theo ngày
        Map<LocalDate, BuocChanNgaySoBuocProjection> dateToData = new HashMap<>();
        for (BuocChanNgaySoBuocProjection r : rows) {
            if (r.getNgay() != null) {
                dateToData.put(r.getNgay(), r);
            }
        }

        // Zero-fill tất cả các ngày trong khoảng
        List<BuocChanNgayPointDTO> result = new ArrayList<>();
        LocalDate d = start;
        while (!d.isAfter(end)) {
            BuocChanNgaySoBuocProjection data = dateToData.get(d);
            if (data != null) {
                result.add(new BuocChanNgayPointDTO(
                        d,
                        data.getSoBuoc() == null ? 0 : data.getSoBuoc(),
                        data.getQuangDuong() == null ? 0f : data.getQuangDuong(),
                        data.getThoiGianGiay() == null ? 0 : data.getThoiGianGiay()
                ));
            } else {
                // Ngày không có dữ liệu - zero fill
                result.add(new BuocChanNgayPointDTO(d, 0, 0f, 0));
            }
            d = d.plusDays(1);
        }
        return result;
    }
}
