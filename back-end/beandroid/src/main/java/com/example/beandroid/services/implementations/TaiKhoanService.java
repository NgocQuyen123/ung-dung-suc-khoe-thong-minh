package com.example.beandroid.services.implementations;

import com.example.beandroid.DTO.CanNangHienTaiRequest;
import com.example.beandroid.DTO.CanNangHienTaiResponse;
import com.example.beandroid.model.MucDoHoatDong;
import com.example.beandroid.model.NhipDoCanNang;
import com.example.beandroid.model.TaiKhoan;
import com.example.beandroid.model.ThongTinCanNang;
import com.example.beandroid.repositories.interfaces.ITaiKhoanRepository;
import com.example.beandroid.repositories.interfaces.IThongTinCanNangRepository;
import com.example.beandroid.services.interfaces.ITaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaiKhoanService implements ITaiKhoanService {
    private final ITaiKhoanRepository taiKhoanRepository;
    @Autowired
    private  IThongTinCanNangRepository thongTinCanNangRepository;
    public TaiKhoanService(ITaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Override
    public List<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanRepository.findAll();
    }


    @Override
    public CanNangHienTaiResponse layCanNangHienTai(Integer idTaiKhoan) {
        return taiKhoanRepository.findById(idTaiKhoan)
                .map(tk -> new CanNangHienTaiResponse(tk.getId(), tk.getCanNang()))
                .orElse(null);
    }

    @Override
    public CanNangHienTaiResponse capNhatCanNangHienTai(Integer idTaiKhoan, CanNangHienTaiRequest req) {
        TaiKhoan tk = taiKhoanRepository.findById(idTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại: " + idTaiKhoan));

        // Cập nhật cân nặng hiện tại
        tk.setCanNang(req.getCanNangHienTai());
        taiKhoanRepository.save(tk);

        // Nếu lưu lịch sử cân nặng
        if (Boolean.TRUE.equals(req.getLuuLichSu())) {
            ThongTinCanNang lichSu = new ThongTinCanNang();
            lichSu.setTaiKhoan(tk);
            lichSu.setCanNangTheoNgay(req.getCanNangHienTai());
            lichSu.setNgayThietLap(LocalDate.now());
            lichSu.setCanNangMucTieu(req.getCanNangMucTieu());

            if (req.getIdMucDoHoatDong() != null) {
                MucDoHoatDong mdhd = new MucDoHoatDong();
                mdhd.setId(req.getIdMucDoHoatDong());
                lichSu.setMucDoHoatDong(mdhd);
            }
            if (req.getIdNhipDoCanNang() != null) {
                NhipDoCanNang ndcn = new NhipDoCanNang();
                ndcn.setId(req.getIdNhipDoCanNang());
                lichSu.setNhipDoCanNang(ndcn);
            }

            thongTinCanNangRepository.save(lichSu);
        }

        return new CanNangHienTaiResponse(idTaiKhoan, req.getCanNangHienTai());
    }
}
