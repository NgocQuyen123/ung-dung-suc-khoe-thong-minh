package com.example.beandroid.controllers;

import com.example.beandroid.services.interfaces.BuocChanService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/buocchan")
public class BuocChanController {

    private final BuocChanService buocChanService;

    public BuocChanController(BuocChanService buocChanService) {
        this.buocChanService = buocChanService;
    }

    @GetMapping(value = "/{idTaiKhoan}", params = "loai")
    public Object getByTime(
            @PathVariable Integer idTaiKhoan,
            @RequestParam String loai,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay,
            @RequestParam(required = false) Integer nam,
            @RequestParam(required = false) Integer thang
    ) {
        // Dùng Object để trả về 2 dạng list: list theo ngày hoặc list theo tháng.
        String t = loai == null ? "" : loai.trim().toLowerCase();
        return switch (t) {
            case "ngay" -> {
                require(ngay != null, "Missing query param: ngay (YYYY-MM-DD) for loai=ngay");
                yield buocChanService.getDay(idTaiKhoan, ngay);
            }
            case "tuan" -> {
                require(ngay != null, "Missing query param: ngay (YYYY-MM-DD) for loai=tuan");
                yield buocChanService.getWeek(idTaiKhoan, ngay);
            }
            case "thang" -> {
                require(nam != null && thang != null, "Missing query params: nam & thang for loai=thang");
                yield buocChanService.getMonth(idTaiKhoan, nam, thang);
            }
            case "nam" -> {
                require(nam != null, "Missing query param: nam for loai=nam");
                yield buocChanService.getYear(idTaiKhoan, nam);
            }
            default -> throw new BadRequestException("Invalid loai. Allowed: ngay|tuan|thang|nam");
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
