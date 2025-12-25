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

    @Column(name = "TocDoKgTuan", nullable = false)
    private Double tocDoKgTuan;

    @Column(name = "CaloThayDoiMoiNgay", nullable = false)
    private Integer caloThayDoiMoiNgay;

    @OneToMany(mappedBy = "nhipDoCanNang")
    private List<ThongTinCanNang> thongTinCanNangs;

    // getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}