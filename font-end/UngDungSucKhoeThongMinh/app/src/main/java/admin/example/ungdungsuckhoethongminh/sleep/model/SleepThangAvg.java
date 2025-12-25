package admin.example.ungdungsuckhoethongminh.sleep.model;

/**
 * Mirrors backend DTO SleepThangAvgDTO (loai=nam).
 */
public class SleepThangAvg {
    public Integer thang; // 1..12

    // averages
    public Integer avgSleepMinutes; // minutes
    public Integer avgBedMinutes;   // minutes from 00:00
    public Integer avgWakeMinutes;  // minutes from 00:00

    // convenience
    public boolean hasData;
    public String avgBedTime;  // HH:mm (nullable)
    public String avgWakeTime; // HH:mm (nullable)
}
