package com.example.beandroid.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "NhipDoCanNang")
public class NhipDoCanNang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TenNDCD", length = 100, nullable = false)
    private String tenNDCD;

    private Integer nhipDoCanNang;

    @OneToMany(mappedBy = "nhipDoCanNang")
    private List<ThongTinCanNang> thongTinCanNangs;

    // getters & setters
}