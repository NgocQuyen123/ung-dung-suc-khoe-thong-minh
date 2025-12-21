package com.example.beandroid.services.interfaces;

import com.example.beandroid.DTO.BuocChanTheoNgayDTO;
import com.example.beandroid.DTO.BuocChanTheoThangDTO;

import java.util.List;

public interface BuocChanService {

    /**
     * Lấy dữ liệu bước chân theo ngày hiện tại.
     */
    List<BuocChanTheoNgayDTO> layTheoNgay(Integer idTaiKhoan);

    /**
     * Lấy dữ liệu 7 ngày gần nhất (bao gồm hôm nay), trả về theo từng ngày.
     */
    List<BuocChanTheoNgayDTO> layTheoTuan(Integer idTaiKhoan);

    /**
     * Lấy dữ liệu 30 ngày gần nhất (bao gồm hôm nay), trả về theo từng ngày.
     */
    List<BuocChanTheoNgayDTO> layTheoThang(Integer idTaiKhoan);

    /**
     * Lấy dữ liệu 12 tháng gần nhất, tổng hợp theo tháng.
     */
    List<BuocChanTheoThangDTO> layTheoNam(Integer idTaiKhoan);
}
