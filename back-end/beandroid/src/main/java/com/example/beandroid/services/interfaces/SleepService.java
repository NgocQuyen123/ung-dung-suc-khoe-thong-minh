package com.example.beandroid.services.interfaces;

import com.example.beandroid.DTO.SleepNgayPointDTO;
import com.example.beandroid.DTO.SleepThangPointDTO;
import com.example.beandroid.DTO.SleepThangAvgDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface cho quản lý dữ liệu giấc ngủ
 * Đồng nhất với BuocChanService
 */
public interface SleepService {

    /**
     * Lấy dữ liệu giấc ngủ theo ngày
     * @param idTaiKhoan ID tài khoản
     * @param ngay Ngày cần lấy
     * @return SleepNgayPointDTO
     */
    List<SleepNgayPointDTO> getDay(Integer idTaiKhoan, LocalDate ngay);

    /**
     * Lấy dữ liệu giấc ngủ theo tuần (7 ngày từ ngày được chọn)
     * @param idTaiKhoan ID tài khoản
     * @param ngay Ngày bất kỳ trong tuần
     * @return List 7 ngày (zero-fill)
     */
    List<SleepNgayPointDTO> getWeek(Integer idTaiKhoan, LocalDate ngay);

    /**
     * Lấy dữ liệu giấc ngủ theo tháng
     * @param idTaiKhoan ID tài khoản
     * @param nam Năm
     * @param thang Tháng (1-12)
     * @return List các ngày trong tháng (zero-fill)
     */
    List<SleepNgayPointDTO> getMonth(Integer idTaiKhoan, Integer nam, Integer thang);

    /**
     * Lấy dữ liệu giấc ngủ theo năm (tổng hợp theo tháng)
     * @param idTaiKhoan ID tài khoản
     * @param nam Năm
     * @return List 12 tháng (zero-fill)
     */
    /**
     * Lấy dữ liệu giấc ngủ theo năm - TRUNG BÌNH theo từng tháng.
     */
    List<SleepThangAvgDTO> getYearAvg(Integer idTaiKhoan, Integer nam);

    /**
     * Lấy dữ liệu giấc ngủ theo năm - TỔNG theo từng tháng (legacy/tuỳ chọn).
     */
    List<SleepThangPointDTO> getYear(Integer idTaiKhoan, Integer nam);

}
