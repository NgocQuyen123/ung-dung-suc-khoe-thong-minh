package com.example.beandroid.services.interfaces;

import com.example.beandroid.DTO.BuocChanNgayPointDTO;
import com.example.beandroid.DTO.BuocChanThangPointDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Service xử lý logic thời gian cho API bước chân (REST).
 * Chỉ giữ API mới theo yêu cầu.
 */
public interface BuocChanService {

    /**
     * type=day: trả 1 phần tử (đúng ngày), nếu không có data thì soBuoc=0.
     */
    List<BuocChanNgayPointDTO> getDay(Integer idTaiKhoan, LocalDate date);

    /**
     * type=week: trả 7 ngày (Thứ 2 -> CN) chứa date truyền vào; zero-fill ngày thiếu.
     */
    List<BuocChanNgayPointDTO> getWeek(Integer idTaiKhoan, LocalDate anyDateInWeek);

    /**
     * type=month: trả tất cả ngày trong tháng; zero-fill ngày thiếu.
     */
    List<BuocChanNgayPointDTO> getMonth(Integer idTaiKhoan, int year, int month);

    /**
     * type=year: trả 12 tháng; zero-fill tháng thiếu.
     */
    List<BuocChanThangPointDTO> getYear(Integer idTaiKhoan, int year);
}
