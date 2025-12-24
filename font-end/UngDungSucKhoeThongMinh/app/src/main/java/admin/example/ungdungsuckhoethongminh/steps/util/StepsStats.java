package admin.example.ungdungsuckhoethongminh.steps.util;

import androidx.annotation.NonNull;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.steps.model.BuocChanNgayPoint;
import admin.example.ungdungsuckhoethongminh.steps.model.BuocChanThangPoint;

/** Utility methods to compute aggregated stats for Steps screens (no UI logic). */
public final class StepsStats {

    private StepsStats() {}

    public static final class DayStats {
        public long totalSteps;
        public long totalKcal;
        public double totalMeters;
        public long totalSeconds;

        public long avgSteps(int count) {
            return count <= 0 ? 0 : Math.round(totalSteps / (double) count);
        }
    }

    @NonNull
    public static DayStats sumDays(@NonNull List<BuocChanNgayPoint> points) {
        DayStats s = new DayStats();
        for (BuocChanNgayPoint p : points) {
            if (p == null) continue;
            s.totalSteps += (p.soBuoc == null ? 0 : p.soBuoc);
            s.totalKcal += (p.kcal == null ? 0 : Math.round(p.kcal));
            s.totalMeters += (p.quangDuong == null ? 0 : p.quangDuong);
            s.totalSeconds += (p.thoiGianGiay == null ? 0 : p.thoiGianGiay);
        }
        return s;
    }

    public static final class MonthStats {
        public long totalSteps;
        public long totalKcal;
        public double totalMeters;
        public long totalSeconds;

        public long avgSteps(int count) {
            return count <= 0 ? 0 : Math.round(totalSteps / (double) count);
        }
    }

    @NonNull
    public static MonthStats sumMonths(@NonNull List<BuocChanThangPoint> months) {
        MonthStats s = new MonthStats();
        for (BuocChanThangPoint m : months) {
            if (m == null) continue;
            s.totalSteps += (m.soBuoc == null ? 0 : m.soBuoc);
            s.totalKcal += (m.kcal == null ? 0 : Math.round(m.kcal));
            s.totalMeters += (m.quangDuong == null ? 0 : m.quangDuong);
            s.totalSeconds += (m.thoiGianGiay == null ? 0 : m.thoiGianGiay);
        }
        return s;
    }
}
