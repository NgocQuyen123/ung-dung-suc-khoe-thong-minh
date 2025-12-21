package com.example.beandroid.services.implementations;

import com.example.beandroid.DTO.BuocChanTheoNgayDTO;
import com.example.beandroid.DTO.BuocChanTheoThangDTO;
import com.example.beandroid.repositories.interfaces.BuocChanNgayRepository;
import com.example.beandroid.services.interfaces.BuocChanService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BuocChanServiceImpl implements BuocChanService {

    private final BuocChanNgayRepository buocChanNgayRepository;

    public BuocChanServiceImpl(BuocChanNgayRepository buocChanNgayRepository) {
        this.buocChanNgayRepository = buocChanNgayRepository;
    }

    @Override
    public List<BuocChanTheoNgayDTO> layTheoNgay(Integer idTaiKhoan) {
        LocalDate today = LocalDate.now();
        return buocChanNgayRepository.findTheoKhoangNgay(idTaiKhoan, today, today);
    }

    @Override
    public List<BuocChanTheoNgayDTO> layTheoTuan(Integer idTaiKhoan) {
        // 7 ngày gần nhất bao gồm hôm nay: [today-6, today]
        LocalDate today = LocalDate.now();
        LocalDate from = today.minusDays(6);
        return buocChanNgayRepository.findTheoKhoangNgay(idTaiKhoan, from, today);
    }

    @Override
    public List<BuocChanTheoNgayDTO> layTheoThang(Integer idTaiKhoan) {
        // 30 ngày gần nhất bao gồm hôm nay: [today-29, today]
        LocalDate today = LocalDate.now();
        LocalDate from = today.minusDays(29);
        return buocChanNgayRepository.findTheoKhoangNgay(idTaiKhoan, from, today);
    }

    @Override
    public List<BuocChanTheoThangDTO> layTheoNam(Integer idTaiKhoan) {
        // 12 tháng gần nhất: lấy theo khoảng ngày để query group-by month.
        // Chọn từ ngày đầu của tháng (today - 11 tháng) đến ngày cuối tháng hiện tại.
        LocalDate today = LocalDate.now();
        LocalDate startMonth = today.minusMonths(11).withDayOfMonth(1);
        LocalDate endDate = today; // nếu UI muốn hết đến hôm nay; có thể đổi sang cuối tháng hiện tại nếu cần.

        return buocChanNgayRepository.tongHopTheoThang(idTaiKhoan, startMonth, endDate);
    }
}
