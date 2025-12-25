package admin.example.ungdungsuckhoethongminh.sleep.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class SleepFormat {

    // Simple semantic colors (similar to UI usage)
    public static final int COLOR_BAD = 0xFFD32F2F;
    public static final int COLOR_OK = 0xFFF57C00;
    public static final int COLOR_GOOD = 0xFF388E3C;
    public static final int COLOR_GREAT = 0xFF2E7D32;

    private SleepFormat() {}

    /**
     * Backend/db may store duration either in minutes or seconds.
     * Heuristic:
     * - If value > 1440 (minutes in a day), treat as seconds.
     * - Else treat as minutes.
     */
    public static int toMinutes(@Nullable Integer minutesOrSeconds) {
        int v = minutesOrSeconds == null ? 0 : Math.max(0, minutesOrSeconds);
        if (v > 1440) {
            return v / 60;
        }
        return v;
    }

    @NonNull
    public static String formatDuration(@Nullable Integer minutesOrSeconds) {
        int m = toMinutes(minutesOrSeconds);
        int h = m / 60;
        int min = m % 60;
        return h + "g " + min + "p";
    }

    /**
     * Format a duration (minutes or seconds) into hours with 1 decimal, e.g. 7.5h.
     */
    @NonNull
    public static String formatHours1dp(@Nullable Integer minutesOrSeconds) {
        int m = toMinutes(minutesOrSeconds);
        float h = m / 60f;
        return String.format(Locale.getDefault(), "%.1fh", h);
    }

    public static int recoveryPercentFromDuration(@Nullable Integer minutesOrSeconds) {
        int m = toMinutes(minutesOrSeconds);
        int pct = Math.round((m / 480f) * 100f);
        if (pct < 0) return 0;
        return Math.min(pct, 100);
    }

    @NonNull
    public static String safeTime(@Nullable String time) {
        return (time == null || time.trim().isEmpty()) ? "--:--" : time;
    }

    /** Parse H:mm (or HH:mm) to minutes since 00:00. Returns null if invalid. */
    @Nullable
    public static Integer parseTimeToMinutes(@Nullable String time) {
        if (time == null) return null;
        String t = time.trim();
        if (t.isEmpty() || !t.contains(":")) return null;
        String[] parts = t.split(":");
        if (parts.length != 2) return null;
        try {
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            if (h < 0 || h > 23 || m < 0 || m > 59) return null;
            return h * 60 + m;
        } catch (Exception e) {
            return null;
        }
    }

    @NonNull
    public static String formatMinutesToTime(int minutes) {
        int m = ((minutes % (24 * 60)) + (24 * 60)) % (24 * 60);
        int h = m / 60;
        int min = m % 60;
        return String.format(Locale.getDefault(), "%d:%02d", h, min);
    }

    /**
     * Sleep quality rating by total sleep duration.
     * Thresholds (minutes):
     * - < 4h  : Tệ
     * - < 6h  : Thiếu
     * - < 8h  : Tạm ổn
     * - >= 8h : Tốt
     */
    @NonNull
    public static SleepQuality qualityFromDuration(@Nullable Integer minutesOrSeconds) {
        int m = toMinutes(minutesOrSeconds);
        if (m < 240) return new SleepQuality("Tệ", COLOR_BAD);
        if (m < 360) return new SleepQuality("Thiếu", COLOR_OK);
        if (m < 480) return new SleepQuality("Tạm ổn", COLOR_OK);
        return new SleepQuality("Tốt", COLOR_GOOD);
    }

    /**
     * Average times on a 24h clock using circular mean.
     * Returns null if no parsable times.
     */
    @Nullable
    public static String averageTime(@NonNull List<String> times) {
        List<Integer> mins = new ArrayList<>();
        for (String t : times) {
            Integer m = parseTimeToMinutes(t);
            if (m != null) mins.add(m);
        }
        if (mins.isEmpty()) return null;

        double sumSin = 0.0;
        double sumCos = 0.0;
        for (int m : mins) {
            double angle = (m / (24.0 * 60.0)) * 2.0 * Math.PI;
            sumCos += Math.cos(angle);
            sumSin += Math.sin(angle);
        }
        double meanAngle = Math.atan2(sumSin / mins.size(), sumCos / mins.size());
        if (meanAngle < 0) meanAngle += 2.0 * Math.PI;
        int meanMin = (int) Math.round((meanAngle / (2.0 * Math.PI)) * 24.0 * 60.0) % (24 * 60);
        return formatMinutesToTime(meanMin);
    }
}
