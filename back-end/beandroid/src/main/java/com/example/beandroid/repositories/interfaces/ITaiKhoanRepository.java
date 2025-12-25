package com.example.beandroid.repositories.interfaces;

import com.example.beandroid.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    // Bạn không cần định nghĩa lại phương thức getAllTaiKhoan, JpaRepository đã có phương thức findAll()
}
