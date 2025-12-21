package com.example.beandroid.controllers;

import com.example.beandroid.DTO.BuocChanTheoNgayDTO;
import com.example.beandroid.DTO.BuocChanTheoThangDTO;
import com.example.beandroid.services.interfaces.BuocChanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/buocchan")
public class BuocChanController {

    private final BuocChanService buocChanService;

    public BuocChanController(BuocChanService buocChanService) {
        this.buocChanService = buocChanService;
    }

    /**
     * 1) Lấy dữ liệu theo NGÀY (hôm nay)
     */
    @GetMapping("/ngay/{id}")
    public List<BuocChanTheoNgayDTO> layTheoNgay(@PathVariable("id") Integer idTaiKhoan) {
        return buocChanService.layTheoNgay(idTaiKhoan);
    }

    /**
     * 2) Lấy dữ liệu theo TUẦN (7 ngày gần nhất, bao gồm hôm nay)
     */
    @GetMapping("/tuan/{id}")
    public List<BuocChanTheoNgayDTO> layTheoTuan(@PathVariable("id") Integer idTaiKhoan) {
        return buocChanService.layTheoTuan(idTaiKhoan);
    }

    /**
     * 3) Lấy dữ liệu theo THÁNG (30 ngày gần nhất, bao gồm hôm nay)
     */
    @GetMapping("/thang/{id}")
    public List<BuocChanTheoNgayDTO> layTheoThang(@PathVariable("id") Integer idTaiKhoan) {
        return buocChanService.layTheoThang(idTaiKhoan);
    }

    /**
     * 4) Lấy dữ liệu theo NĂM (12 tháng gần nhất, tổng hợp theo tháng)
     */
    @GetMapping("/nam/{id}")
    public List<BuocChanTheoThangDTO> layTheoNam(@PathVariable("id") Integer idTaiKhoan) {
        return buocChanService.layTheoNam(idTaiKhoan);
    }
}
