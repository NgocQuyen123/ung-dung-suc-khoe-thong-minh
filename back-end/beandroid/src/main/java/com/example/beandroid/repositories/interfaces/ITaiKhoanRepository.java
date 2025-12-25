package com.example.beandroid.repositories.interfaces;

import com.example.beandroid.DTO.LoginResponse;
import com.example.beandroid.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {

    boolean existsBySdt(String sdt);

    Optional<TaiKhoan> findBySdt(String sdt);
}
