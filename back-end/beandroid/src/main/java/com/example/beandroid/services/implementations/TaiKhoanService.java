package com.example.beandroid.services.implementations;

import com.example.beandroid.DTO.CreateTaiKhoanRequest;
import com.example.beandroid.DTO.LoginResponse;
import com.example.beandroid.model.TaiKhoan;
import com.example.beandroid.repositories.interfaces.ITaiKhoanRepository;
import com.example.beandroid.services.interfaces.ITaiKhoanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaiKhoanService implements ITaiKhoanService {
    private final ITaiKhoanRepository taiKhoanRepository;

    public TaiKhoanService(ITaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Override
    public List<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanRepository.findAll();
    }

    @Override
    public TaiKhoan create(CreateTaiKhoanRequest request) {

        if (taiKhoanRepository.existsBySdt(request.getSdt())) {
            throw new RuntimeException("sdt đã tồn tại");
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setSdt(request.getSdt());
        tk.setTenTK(request.getTenTK());
        tk.setGioiTinh(request.getGioiTinh());
        tk.setChieuCao(request.getChieuCao());
        tk.setNamSinh(request.getNamSinh());
        tk.setCanNang(request.getCanNang());

        return taiKhoanRepository.save(tk);
    }

    @Override
    public LoginResponse loginBySdt(String sdt) {
        return taiKhoanRepository.findBySdt(sdt)
                .map(taiKhoan -> new LoginResponse(true, "Đăng nhập thành công", taiKhoan))
                .orElse(new LoginResponse(false, "Số điện thoại chưa đăng ký", null));
    }

}
