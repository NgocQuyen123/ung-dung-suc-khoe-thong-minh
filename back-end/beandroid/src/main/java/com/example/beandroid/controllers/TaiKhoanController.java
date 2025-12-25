package com.example.beandroid.controllers;

import com.example.beandroid.DTO.CanNangHienTaiRequest;
import com.example.beandroid.DTO.CanNangHienTaiResponse;
import com.example.beandroid.model.TaiKhoan;
import com.example.beandroid.services.interfaces.ITaiKhoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taikhoan")
public class TaiKhoanController {

    private final ITaiKhoanService taiKhoanService;

    public TaiKhoanController(ITaiKhoanService taiKhoanService) {
        this.taiKhoanService = taiKhoanService;
    }

    // API TEST KẾT NỐI DATABASE
    @GetMapping
    public List<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanService.getAllTaiKhoan();
    }
    @GetMapping("/{id}/can-nang-hien-tai")
    public ResponseEntity<CanNangHienTaiResponse> layCanNangHienTai(@PathVariable Integer id) {
        CanNangHienTaiResponse res = taiKhoanService.layCanNangHienTai(id);
        if (res == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(res);
    }


    @PutMapping("/{id}/can-nang-hien-tai")
    public ResponseEntity<?> capNhatCanNangHienTai(@PathVariable Integer id,
                                                   @RequestBody CanNangHienTaiRequest req) {
        try {
            CanNangHienTaiResponse res = taiKhoanService.capNhatCanNangHienTai(id, req);
            return ResponseEntity.ok(res);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }
}
