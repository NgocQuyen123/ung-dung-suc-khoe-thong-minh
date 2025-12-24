package admin.example.ungdungsuckhoethongminh.steps.util;

import java.util.Locale;

public final class StepsFormat {

    private StepsFormat() {}

    public static String formatKmFromMeters(float meters) {
        if (meters < 1000f) {
            return String.format(Locale.getDefault(), "%.0f m", meters);
        }
        return String.format(Locale.getDefault(), "%.1f km", (meters / 1000f));
    }

    public static String formatMinutesFromSeconds(int seconds) {
        int minutes = Math.round(seconds / 60f);
        return minutes + "\nphút";
    }

    public static String formatStepsTile(Integer steps) {
        if (steps == null) steps = 0;
        return steps + "\nbước";
    }

    public static String formatKcalTile(float kcal) {
        return String.format(Locale.getDefault(), "%.0f\nkcal", kcal);
    }

    public static String formatKcalTile(long kcal) {
        return kcal + "\nkcal";
    }
}
