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

    @GetMapping(value = "/{idTaiKhoan}", params = "type")
    public Object getByTime(
            @PathVariable Integer idTaiKhoan,
            @RequestParam String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        // Dùng Object để trả về 2 dạng list: list theo ngày hoặc list theo tháng.
        String t = type == null ? "" : type.trim().toLowerCase();
        return switch (t) {
            case "day" -> {
                require(date != null, "Missing query param: date (YYYY-MM-DD) for type=day");
                yield buocChanService.getDay(idTaiKhoan, date);
            }
            case "week" -> {
                require(date != null, "Missing query param: date (YYYY-MM-DD) for type=week");
                yield buocChanService.getWeek(idTaiKhoan, date);
            }
            case "month" -> {
                require(year != null && month != null, "Missing query params: year & month for type=month");
                yield buocChanService.getMonth(idTaiKhoan, year, month);
            }
            case "year" -> {
                require(year != null, "Missing query param: year for type=year");
                yield buocChanService.getYear(idTaiKhoan, year);
            }
            default -> throw new BadRequestException("Invalid type. Allowed: day|week|month|year");
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
