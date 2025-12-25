package admin.example.ungdungsuckhoethongminh.fragments.sleep;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeActivity;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeGetBedActivity;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeGetUpActivity;
import admin.example.ungdungsuckhoethongminh.sleep.model.SleepNgayPoint;
import admin.example.ungdungsuckhoethongminh.sleep.repository.SleepRepository;
import admin.example.ungdungsuckhoethongminh.sleep.util.SleepFormat;
import admin.example.ungdungsuckhoethongminh.sleep.util.SleepQuality;
import admin.example.ungdungsuckhoethongminh.sleep.util.SleepUserResolver;

public class SleepMonthFragment extends Fragment {

    private LinearLayout row1, row2, row3, row4, row5;
    private Calendar calendar;
    private TextView selectedDayView = null;

    private ImageButton btnPrevDay, btnNextDay;
    private TextView txtDayInfo;

    private TextView txtTotalSteps;
    private TextView txtSleepDuration;
    private TextView txtBedtime;
    private TextView txtWakeTime;
    private TextView txtSleepQuality;

    private final SleepRepository repository = new SleepRepository();

    // Cache data by day-of-month (1..31)
    private final Map<Integer, SleepNgayPoint> dayData = new HashMap<>();

    private Integer selectedDayOfMonth = null;

    public SleepMonthFragment() {}

    private int getTaiKhoanId() {
        return SleepUserResolver.resolveIdTaiKhoan(requireContext(), getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sleep_month, container, false);

        btnPrevDay = view.findViewById(R.id.btnPrevDay);
        btnNextDay = view.findViewById(R.id.btnNextDay);
        txtDayInfo = view.findViewById(R.id.txtDayInfo);

        txtTotalSteps = view.findViewById(R.id.txtTotalSteps);
        txtSleepDuration = view.findViewById(R.id.txtSleepDuration);
        txtBedtime = view.findViewById(R.id.txtBedtime);
        txtWakeTime = view.findViewById(R.id.txtWakeTime);
        txtSleepQuality = view.findViewById(R.id.txtSleepQuality);

        row1 = view.findViewById(R.id.row1);
        row2 = view.findViewById(R.id.row2);
        row3 = view.findViewById(R.id.row3);
        row4 = view.findViewById(R.id.row4);
        row5 = view.findViewById(R.id.row5);

        calendar = Calendar.getInstance();

        btnPrevDay.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            selectedDayOfMonth = null;
            renderCalendar();
            fetchAndRenderMonth();
        });

        btnNextDay.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1);
            selectedDayOfMonth = null;
            renderCalendar();
            fetchAndRenderMonth();
        });

        // Detail screens
        LinearLayout cardSleep = view.findViewById(R.id.cardSleepDuration);
        cardSleep.setOnClickListener(v -> startActivity(new Intent(getActivity(), SleepTimeActivity.class)));

        LinearLayout cardSleepBed = view.findViewById(R.id.cardSleepBed);
        cardSleepBed.setOnClickListener(v -> startActivity(new Intent(getActivity(), SleepTimeGetBedActivity.class)));

        LinearLayout cardSleepUp = view.findViewById(R.id.cardSleepUp);
        cardSleepUp.setOnClickListener(v -> startActivity(new Intent(getActivity(), SleepTimeGetUpActivity.class)));

        renderCalendar();
        fetchAndRenderMonth();
        return view;
    }

    private void fetchAndRenderMonth() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        txtDayInfo.setText(String.format(Locale.getDefault(), "Tháng %d/%d", month, year));

        repository.getThang(getTaiKhoanId(), year, month, new SleepRepository.ResultCallback<List<SleepNgayPoint>>() {
            @Override
            public void onSuccess(@NonNull List<SleepNgayPoint> data) {
                if (!isAdded()) return;
                buildDayMap(data);
                ensureDefaultSelection();
                renderSelectedDayOrAvg();
            }

            @Override
            public void onError(@NonNull Throwable t) {
                if (!isAdded()) return;
                dayData.clear();
                ensureDefaultSelection();
                renderSelectedDayOrAvg();
            }
        });
    }

    private void buildDayMap(@NonNull List<SleepNgayPoint> days) {
        dayData.clear();
        for (SleepNgayPoint p : days) {
            if (p == null || p.ngay == null) continue;
            // ngay is yyyy-MM-dd
            int d = parseDayOfMonth(p.ngay);
            if (d >= 1 && d <= 31) {
                dayData.put(d, p);
            }
        }
    }

    private int parseDayOfMonth(@NonNull String yyyyMMdd) {
        try {
            int idx = yyyyMMdd.lastIndexOf('-');
            if (idx < 0) return -1;
            return Integer.parseInt(yyyyMMdd.substring(idx + 1));
        } catch (Exception e) {
            return -1;
        }
    }

    private void ensureDefaultSelection() {
        if (selectedDayOfMonth != null) return;

        Calendar now = Calendar.getInstance();
        boolean sameMonth = now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && now.get(Calendar.MONTH) == calendar.get(Calendar.MONTH);

        if (sameMonth) {
            selectedDayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        } else {
            selectedDayOfMonth = 1;
        }

        // Update UI highlight if calendar already rendered.
        highlightSelectedDay();
    }

    private void renderSelectedDayOrAvg() {
        SleepNgayPoint p = (selectedDayOfMonth == null) ? null : dayData.get(selectedDayOfMonth);
        if (p != null) {
            renderPoint(p);
        } else {
            // No point for selected day -> render empty (0)
            renderPoint(null);
        }
    }

    private void renderPoint(@Nullable SleepNgayPoint p) {
        Integer dur = (p == null) ? null : p.tongThoiGianNgu;
        int pct = SleepFormat.recoveryPercentFromDuration(dur);

        if (txtTotalSteps != null) txtTotalSteps.setText(String.valueOf(pct));
        if (txtSleepDuration != null) txtSleepDuration.setText(SleepFormat.formatDuration(dur));
        if (txtBedtime != null) txtBedtime.setText(SleepFormat.safeTime(p == null ? null : p.gioBatDau));
        if (txtWakeTime != null) txtWakeTime.setText(SleepFormat.safeTime(p == null ? null : p.gioKetThuc));

        SleepQuality q = SleepFormat.qualityFromDuration(dur);
        if (txtSleepQuality != null) {
            txtSleepQuality.setText(q.label);
            txtSleepQuality.setTextColor(q.color);
        }
    }

    private void renderCalendar() {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        List<LinearLayout> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);

        for (LinearLayout row : rows) {
            row.removeAllViews();
        }

        Calendar tempCal = Calendar.getInstance();
        tempCal.set(year, month, 1);

        int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1; // CN=1
        if (startDayOfWeek == 0) startDayOfWeek = 7;

        int day = 1;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (int i = 0; i < 5; i++) {
            LinearLayout row = rows.get(i);

            for (int col = 1; col <= 7; col++) {
                TextView dayView = (TextView) inflater.inflate(R.layout.item_month_circle, row, false);

                if (i == 0 && col < startDayOfWeek) {
                    dayView.setText("");
                    row.addView(dayView);
                    continue;
                }

                if (day > daysInMonth) {
                    dayView.setText("");
                    row.addView(dayView);
                    continue;
                }

                final int dayNumber = day;
                dayView.setText(String.valueOf(dayNumber));

                Calendar now = Calendar.getInstance();
                int today = now.get(Calendar.DAY_OF_MONTH);
                int thisMonth = now.get(Calendar.MONTH);
                int thisYear = now.get(Calendar.YEAR);

                if (dayNumber == today && month == thisMonth && year == thisYear) {
                    dayView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_today));
                    dayView.setTypeface(Typeface.DEFAULT_BOLD);
                }

                dayView.setOnClickListener(v -> {
                    onDaySelected((TextView) v);
                    selectedDayOfMonth = dayNumber;
                    renderSelectedDayOrAvg();
                });

                row.addView(dayView);
                day++;
            }
        }

        // After rebuild, apply selection highlight
        highlightSelectedDay();
    }

    private void highlightSelectedDay() {
        if (getView() == null || selectedDayOfMonth == null) return;

        // Reset previously selected
        if (selectedDayView != null) {
            selectedDayView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_normal));
            selectedDayView.setTextColor(Color.BLACK);
            selectedDayView.setTypeface(Typeface.DEFAULT);
        }

        // Find TextView by scanning rows
        List<LinearLayout> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);

        for (LinearLayout row : rows) {
            for (int i = 0; i < row.getChildCount(); i++) {
                View child = row.getChildAt(i);
                if (child instanceof TextView) {
                    TextView tv = (TextView) child;
                    String txt = tv.getText() == null ? "" : tv.getText().toString();
                    if (txt.equals(String.valueOf(selectedDayOfMonth))) {
                        tv.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_selected));
                        tv.setTextColor(Color.WHITE);
                        tv.setTypeface(Typeface.DEFAULT_BOLD);
                        selectedDayView = tv;
                        return;
                    }
                }
            }
        }
    }

    private void onDaySelected(TextView dayView) {
        // Keep original behavior for selection visuals
        if (selectedDayView != null) {
            selectedDayView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_normal));
            selectedDayView.setTextColor(Color.BLACK);
            selectedDayView.setTypeface(Typeface.DEFAULT);
        }

        dayView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_selected));
        dayView.setTextColor(Color.WHITE);
        dayView.setTypeface(Typeface.DEFAULT_BOLD);
        selectedDayView = dayView;
    }
}
