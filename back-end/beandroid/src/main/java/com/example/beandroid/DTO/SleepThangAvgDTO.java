package com.example.beandroid.DTO;

public class SleepThangAvgDTO {

    private Integer thang; // 1..12
    private Integer avgSleepMinutes; // phút
    private Integer avgBedMinutes;   // phút từ 00:00
    private Integer avgWakeMinutes;  // phút từ 00:00

    // Convenience fields for Android
    private boolean hasData;    // true nếu tháng có dữ liệu thực
    private String avgBedTime;  // HH:mm
    private String avgWakeTime; // HH:mm

    public SleepThangAvgDTO() {
    }

    public SleepThangAvgDTO(
            Integer thang,
            Double avgSleepMinutes,
            Double avgBedMinutes,
            Double avgWakeMinutes
    ) {
        this.thang = thang;
        this.avgSleepMinutes = avgSleepMinutes == null ? 0 : avgSleepMinutes.intValue();
        this.avgBedMinutes = avgBedMinutes == null ? 0 : avgBedMinutes.intValue();
        this.avgWakeMinutes = avgWakeMinutes == null ? 0 : avgWakeMinutes.intValue();
    }

    public SleepThangAvgDTO(Integer thang, Integer avgSleepMinutes, Integer avgBedMinutes, Integer avgWakeMinutes) {
        this.thang = thang;
        this.avgSleepMinutes = avgSleepMinutes == null ? 0 : avgSleepMinutes;
        this.avgBedMinutes = avgBedMinutes == null ? 0 : avgBedMinutes;
        this.avgWakeMinutes = avgWakeMinutes == null ? 0 : avgWakeMinutes;
    }

    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Integer getAvgSleepMinutes() {
        return avgSleepMinutes;
    }

    public void setAvgSleepMinutes(Integer avgSleepMinutes) {
        this.avgSleepMinutes = avgSleepMinutes;
    }

    public Integer getAvgBedMinutes() {
        return avgBedMinutes;
    }

    public void setAvgBedMinutes(Integer avgBedMinutes) {
        this.avgBedMinutes = avgBedMinutes;
    }

    public Integer getAvgWakeMinutes() {
        return avgWakeMinutes;
    }

    public void setAvgWakeMinutes(Integer avgWakeMinutes) {
        this.avgWakeMinutes = avgWakeMinutes;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public String getAvgBedTime() {
        return avgBedTime;
    }

    public void setAvgBedTime(String avgBedTime) {
        this.avgBedTime = avgBedTime;
    }

    public String getAvgWakeTime() {
        return avgWakeTime;
    }

    public void setAvgWakeTime(String avgWakeTime) {
        this.avgWakeTime = avgWakeTime;
    }
}