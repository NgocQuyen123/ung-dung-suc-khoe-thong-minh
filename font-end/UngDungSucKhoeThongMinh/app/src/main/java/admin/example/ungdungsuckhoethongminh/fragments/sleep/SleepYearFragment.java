package admin.example.ungdungsuckhoethongminh.fragments.sleep;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeActivity;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeGetBedActivity;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeGetUpActivity;
import admin.example.ungdungsuckhoethongminh.sleep.model.SleepThangAvg;
import admin.example.ungdungsuckhoethongminh.sleep.repository.SleepRepository;
import admin.example.ungdungsuckhoethongminh.sleep.util.SleepFormat;
import admin.example.ungdungsuckhoethongminh.sleep.util.SleepUserResolver;

public class SleepYearFragment extends Fragment {

    // Cache year data for click interactions
    private List<SleepThangAvg> cachedMonths = new ArrayList<>();

    // Cached "year average" values to restore when nothing selected
    private int cachedYearAvgPerDayMinutes = 0;
    private String cachedYearBedAvg = null;
    private String cachedYearWakeAvg = null;

    private ImageButton btnPrevYear, btnNextYear;
    private TextView tvYear;
    private TextView tvRecoveryRate;
    private BarChart barChart;

    private TextView yearSleepDuration;
    private TextView yearBedtime;
    private TextView yearWakeTime;

    private final Calendar calendar = Calendar.getInstance();
    private final SleepRepository repository = new SleepRepository();

    public SleepYearFragment() {}

    private int getTaiKhoanId() {
        return SleepUserResolver.resolveIdTaiKhoan(requireContext(), getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sleep_year, container, false);

        btnPrevYear = view.findViewById(R.id.btnPrevYear);
        btnNextYear = view.findViewById(R.id.btnNextYear);
        tvYear = view.findViewById(R.id.tvYear);
        tvRecoveryRate = view.findViewById(R.id.tvRecoveryRate);
        barChart = view.findViewById(R.id.barChart);

        yearSleepDuration = view.findViewById(R.id.txtYearSleepDuration);
        yearBedtime = view.findViewById(R.id.txtYearBedtime);
        yearWakeTime = view.findViewById(R.id.txtYearWakeTime);

        btnPrevYear.setOnClickListener(v -> {
            calendar.add(Calendar.YEAR, -1);
            fetchAndRender();
        });

        btnNextYear.setOnClickListener(v -> {
            calendar.add(Calendar.YEAR, 1);
            fetchAndRender();
        });

        // Detail screens
        LinearLayout cardSleep = view.findViewById(R.id.cardSleepDuration);
        cardSleep.setOnClickListener(v -> startActivity(new Intent(getActivity(), SleepTimeActivity.class)));

        LinearLayout cardSleepBed = view.findViewById(R.id.cardSleepBed);
        cardSleepBed.setOnClickListener(v -> startActivity(new Intent(getActivity(), SleepTimeGetBedActivity.class)));

        LinearLayout cardSleepUp = view.findViewById(R.id.cardSleepUp);
        cardSleepUp.setOnClickListener(v -> startActivity(new Intent(getActivity(), SleepTimeGetUpActivity.class)));

        setupChart();
        fetchAndRender();
        return view;
    }

    private void fetchAndRender() {
        int year = calendar.get(Calendar.YEAR);
        if (tvYear != null) tvYear.setText(String.valueOf(year));

        repository.getNam(getTaiKhoanId(), year, new SleepRepository.ResultCallback<List<SleepThangAvg>>() {
            @Override
            public void onSuccess(@NonNull List<SleepThangAvg> data) {
                if (!isAdded()) return;
                renderYear(data);
            }

            @Override
            public void onError(@NonNull Throwable t) {
                if (!isAdded()) return;
                renderYear(new ArrayList<>());
            }
        });
    }

    private void setupChart() {
        if (barChart == null) return;

        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setDrawGridBackground(false);

        barChart.setHighlightPerTapEnabled(true);
        barChart.setHighlightPerDragEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int month = Math.round(e.getX());
                applyMonthSelection(month);
            }

            @Override
            public void onNothingSelected() {
                applyYearSummary();
            }
        });

        XAxis x = barChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setGranularity(1f);
        x.setDrawGridLines(false);
        x.setTextColor(Color.DKGRAY);
        x.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int m = (int) value;
                if (m >= 1 && m <= 12) return String.valueOf(m);
                return "";
            }
        });

        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setTextColor(Color.DKGRAY);
        barChart.getAxisLeft().setAxisMinimum(0f);
    }

    private void renderYear(@NonNull List<SleepThangAvg> months) {
        cachedMonths = months;
        long totalMinutes = 0L;
        int count = 0;

        List<BarEntry> entries = new ArrayList<>();
        List<String> bedTimes = new ArrayList<>();
        List<String> wakeTimes = new ArrayList<>();

        for (int m = 1; m <= 12; m++) {
            SleepThangAvg month = null;
            for (SleepThangAvg p : months) {
                if (p != null && p.thang != null && p.thang == m) {
                    month = p;
                    break;
                }
            }

            int avgSleepMinutes = month == null || month.avgSleepMinutes == null ? 0 : month.avgSleepMinutes;
            float hours = avgSleepMinutes / 60f;
            entries.add(new BarEntry(m, hours));

            totalMinutes += avgSleepMinutes;
            count++;

            if (month != null && month.hasData) {
                if (month.avgBedTime != null) bedTimes.add(month.avgBedTime);
                if (month.avgWakeTime != null) wakeTimes.add(month.avgWakeTime);
            }
        }

        // Average bedtime/wake time across months (circular mean)
        String bedAvg = SleepFormat.averageTime(bedTimes);
        String wakeAvg = SleepFormat.averageTime(wakeTimes);
        cachedYearBedAvg = bedAvg;
        cachedYearWakeAvg = wakeAvg;
        if (yearBedtime != null) yearBedtime.setText(SleepFormat.safeTime(bedAvg));
        if (yearWakeTime != null) yearWakeTime.setText(SleepFormat.safeTime(wakeAvg));

        // Average per day over the whole year (more meaningful than per-month average)
        int year = calendar.get(Calendar.YEAR);
        Calendar tmp = Calendar.getInstance();
        tmp.clear();
        tmp.set(Calendar.YEAR, year);
        int daysInYear = tmp.getActualMaximum(Calendar.DAY_OF_YEAR);

        int avgPerDay = daysInYear <= 0 ? 0 : Math.round(totalMinutes / (float) daysInYear);
        cachedYearAvgPerDayMinutes = avgPerDay;
        int pct = SleepFormat.recoveryPercentFromDuration(avgPerDay);
        if (tvRecoveryRate != null) tvRecoveryRate.setText(pct + "%");

        if (barChart != null) {
            BarDataSet set = new BarDataSet(entries, "Sleep");
            set.setColor(Color.parseColor("#8890EA"));
            set.setValueTextColor(Color.DKGRAY);
            set.setValueTextSize(10f);
            set.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.format(java.util.Locale.getDefault(), "%.1fh", value);
                }
            });

            BarData data = new BarData(set);
            data.setBarWidth(0.7f);

            barChart.setData(data);
            barChart.invalidate();
        }

        if (yearSleepDuration != null) yearSleepDuration.setText(SleepFormat.formatDuration(avgPerDay));

        // Default state = year summary (no month selected)
        if (barChart != null) {
            barChart.highlightValues(null);
        }
    }

    private void applyMonthSelection(int month) {
        if (!isAdded()) return;
        SleepThangAvg m = null;
        for (SleepThangAvg p : cachedMonths) {
            if (p != null && p.thang != null && p.thang == month) {
                m = p;
                break;
            }
        }
        Integer avgSleepMinutes = (m == null) ? 0 : (m.avgSleepMinutes == null ? 0 : m.avgSleepMinutes);

        // Update cards by selected month
        if (yearSleepDuration != null) yearSleepDuration.setText(SleepFormat.formatDuration(avgSleepMinutes));
        if (yearBedtime != null) yearBedtime.setText(SleepFormat.safeTime(m == null ? null : m.avgBedTime));
        if (yearWakeTime != null) yearWakeTime.setText(SleepFormat.safeTime(m == null ? null : m.avgWakeTime));

        int pct = SleepFormat.recoveryPercentFromDuration(avgSleepMinutes);
        if (tvRecoveryRate != null) tvRecoveryRate.setText(pct + "%");

        // Optional: show selected month in header
        if (tvYear != null) {
            int y = calendar.get(Calendar.YEAR);
            tvYear.setText("Tháng " + month + "/" + y);
        }
    }

    private void applyYearSummary() {
        if (!isAdded()) return;
        if (yearSleepDuration != null) yearSleepDuration.setText(SleepFormat.formatDuration(cachedYearAvgPerDayMinutes));
        if (yearBedtime != null) yearBedtime.setText(SleepFormat.safeTime(cachedYearBedAvg));
        if (yearWakeTime != null) yearWakeTime.setText(SleepFormat.safeTime(cachedYearWakeAvg));

        int pct = SleepFormat.recoveryPercentFromDuration(cachedYearAvgPerDayMinutes);
        if (tvRecoveryRate != null) tvRecoveryRate.setText(pct + "%");

        if (tvYear != null) {
            tvYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        }
    }
}
