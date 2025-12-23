package com.example.beandroid.services.interfaces;

/**
 * Kiểu truy vấn dữ liệu bước chân.
 * Dùng enum để tránh magic string và dễ mở rộng (vd: RANGE sau này).
 */
public enum BuocChanQueryType {
    DAY,
    WEEK,
    MONTH,
    YEAR
}
