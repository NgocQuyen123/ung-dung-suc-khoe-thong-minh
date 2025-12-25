package com.example.beandroid.services.implementations;

import com.example.beandroid.DTO.CreateTaiKhoanRequest;
import com.example.beandroid.DTO.LoginResponse;
import com.example.beandroid.model.TaiKhoan;
import org.springframework.stereotype.Service;

import com.example.beandroid.DTO.TaiKhoanDTO;
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

    @Autowired
    private final ITaiKhoanRepository repository;

    @Autowired
    private  IThongTinCanNangRepository thongTinCanNangRepository;

    public TaiKhoanService(ITaiKhoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TaiKhoan> getAllTaiKhoan() {
        return repository.findAll();
    }

    @Override
    public TaiKhoan getTaiKhoanById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
    }

    @Override
    public TaiKhoan updateTaiKhoan(TaiKhoanDTO taiKhoanDTO) {

        Integer id = taiKhoanDTO.getId();
        if (id == null) {
            throw new RuntimeException("ID không được null");
        }

        TaiKhoan taiKhoan = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        // Ánh xạ từ DTO sang entity
        taiKhoan.setSdt(taiKhoanDTO.getSdt());
        taiKhoan.setTenTK(taiKhoanDTO.getTenTK());
        taiKhoan.setGioiTinh(taiKhoanDTO.getGioiTinh());
        taiKhoan.setChieuCao(taiKhoanDTO.getChieuCao());
        taiKhoan.setNamSinh(taiKhoanDTO.getNamSinh());
        taiKhoan.setCanNang(taiKhoanDTO.getCanNang());

        return repository.save(taiKhoan);
    }

    @Override
    public TaiKhoan create(CreateTaiKhoanRequest request) {

        if (repository.existsBySdt(request.getSdt())) {
            throw new RuntimeException("sdt đã tồn tại");
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setSdt(request.getSdt());
        tk.setTenTK(request.getTenTK());
        tk.setGioiTinh(request.getGioiTinh());
        tk.setChieuCao(request.getChieuCao());
        tk.setNamSinh(request.getNamSinh());
        tk.setCanNang(request.getCanNang());

        return repository.save(tk);
    }

    @Override
    public LoginResponse loginBySdt(String sdt) {
        return repository.findBySdt(sdt)
                .map(taiKhoan -> new LoginResponse(true, "Đăng nhập thành công", taiKhoan))
                .orElse(new LoginResponse(false, "Số điện thoại chưa đăng ký", null));
    }


    @Override
    public CanNangHienTaiResponse layCanNangHienTai(Integer idTaiKhoan) {
        return repository.findById(idTaiKhoan)
                .map(tk -> new CanNangHienTaiResponse(tk.getId(), tk.getCanNang()))
                .orElse(null);
    }

    @Override
    public CanNangHienTaiResponse capNhatCanNangHienTai(Integer idTaiKhoan, CanNangHienTaiRequest req) {
        TaiKhoan tk = repository.findById(idTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại: " + idTaiKhoan));

        // Cập nhật cân nặng hiện tại
        tk.setCanNang(req.getCanNangHienTai());
        repository.save(tk);

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
