package com.example.beandroid.controllers;

import com.example.beandroid.services.interfaces.SleepService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST Controller cho quản lý dữ liệu giấc ngủ.
 * Pattern tương tự BuocChanController.
 */
@RestController
@RequestMapping("/api/sleep")
public class SleepController {

    private final SleepService sleepService;

    public SleepController(SleepService sleepService) {
        this.sleepService = sleepService;
    }

    /**
     * API thống nhất: Lấy giấc ngủ theo thời gian
     * GET /api/sleep/{idTaiKhoan}?loai=ngay|tuan|thang|nam|nam_tong&ngay=...&nam=...&thang=...
     */
    @GetMapping(value = "/{idTaiKhoan}", params = "loai")
    public Object getByTime(
            @PathVariable Integer idTaiKhoan,
            @RequestParam String loai,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay,
            @RequestParam(required = false) Integer nam,
            @RequestParam(required = false) Integer thang
    ) {
        String t = loai == null ? "" : loai.trim().toLowerCase();
        return switch (t) {
            case "ngay" -> {
                require(ngay != null, "Missing query param: ngay (YYYY-MM-DD) for loai=ngay");
                yield sleepService.getDay(idTaiKhoan, ngay);
            }
            case "tuan" -> {
                require(ngay != null, "Missing query param: ngay (YYYY-MM-DD) for loai=tuan");
                yield sleepService.getWeek(idTaiKhoan, ngay);
            }
            case "thang" -> {
                require(nam != null && thang != null, "Missing query params: nam & thang for loai=thang");
                yield sleepService.getMonth(idTaiKhoan, nam, thang);
            }
            case "nam" -> {
                require(nam != null, "Missing query param: nam for loai=nam");
                // loai=nam trả về TRUNG BÌNH theo tháng (phục vụ chart + avg bedtime/wake)
                yield sleepService.getYearAvg(idTaiKhoan, nam);
            }
            case "nam_tong", "namtong", "nam_tonghop" -> {
                require(nam != null, "Missing query param: nam for loai=nam_tong");
                // Legacy: tổng theo tháng
                yield sleepService.getYear(idTaiKhoan, nam);
            }
            default -> throw new BadRequestException(
                    "Invalid loai. Allowed: ngay|tuan|thang|nam|nam_tong|namtong|nam_tonghop"
            );
        };
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new BadRequestException(message);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }
}
