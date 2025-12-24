package admin.example.ungdungsuckhoethongminh.fragments.step;

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
import admin.example.ungdungsuckhoethongminh.steps.model.BuocChanNgayPoint;
import admin.example.ungdungsuckhoethongminh.steps.repository.StepsRepository;
import admin.example.ungdungsuckhoethongminh.steps.util.StepsFormat;

public class StepMonthFragment extends Fragment {

    private LinearLayout row1, row2, row3, row4, row5;
    private TextView txtWeekRange;
    private TextView txtTotalSteps, txtAverage;
    private TextView txtSmallSteps, txtSmallCalories, txtSmallDistance, txtSmallTime;
    private ImageButton btnPrevDay, btnNextDay;

    private Calendar calendar;
    private TextView selectedDayView;

    private final StepsRepository repository = new StepsRepository();
    private final Map<Integer, BuocChanNgayPoint> dayToPoint = new HashMap<>();

    public StepMonthFragment() {}

    private int getTaiKhoanId() {
        Bundle args = getArguments();
        if (args != null && args.containsKey("idTaiKhoan")) {
            return args.getInt("idTaiKhoan", 1);
        }
        return admin.example.ungdungsuckhoethongminh.steps.session.StepsUserSession.getIdTaiKhoan(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_month, container, false);

        btnPrevDay = view.findViewById(R.id.btnPrevDay);
        btnNextDay = view.findViewById(R.id.btnNextDay);

        row1 = view.findViewById(R.id.row1);
        row2 = view.findViewById(R.id.row2);
        row3 = view.findViewById(R.id.row3);
        row4 = view.findViewById(R.id.row4);
        row5 = view.findViewById(R.id.row5);

        txtWeekRange = view.findViewById(R.id.txtWeekRange);
        txtTotalSteps = view.findViewById(R.id.txtTotalSteps);
        txtAverage = view.findViewById(R.id.txtAverage);

        txtSmallSteps = view.findViewById(R.id.txtSmallSteps);
        txtSmallCalories = view.findViewById(R.id.txtSmallCalories);
        txtSmallDistance = view.findViewById(R.id.txtSmallDistance);
        txtSmallTime = view.findViewById(R.id.txtSmallTime);

        calendar = Calendar.getInstance();

        btnPrevDay.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            renderCalendar();
            fetchMonth();
        });

        btnNextDay.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1);
            renderCalendar();
            fetchMonth();
        });

        renderCalendar();
        fetchMonth();

        return view;
    }

    private void fetchMonth() {
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        repository.getThang(getTaiKhoanId(), year, month, new StepsRepository.ResultCallback<List<BuocChanNgayPoint>>() {
            @Override
            public void onSuccess(@NonNull List<BuocChanNgayPoint> data) {
                if (!isAdded()) return;
                dayToPoint.clear();
                for (BuocChanNgayPoint p : data) {
                    if (p == null || p.ngay == null || p.ngay.length() < 10) continue;
                    try {
                        int d = Integer.parseInt(p.ngay.substring(8, 10));
                        dayToPoint.put(d, p);
                    } catch (Exception ignored) {}
                }
                renderMonthStats(data);
            }

            @Override
            public void onError(@NonNull Throwable t) {
                if (!isAdded()) return;
                dayToPoint.clear();
                renderMonthStats(new ArrayList<>());
            }
        });
    }

    private void renderMonthStats(@NonNull List<BuocChanNgayPoint> points) {
        long totalSteps = 0;
        double totalMeters = 0;
        long totalSeconds = 0;

        for (BuocChanNgayPoint p : points) {
            if (p == null) continue;
            totalSteps += (p.soBuoc == null ? 0 : p.soBuoc);
            totalMeters += (p.quangDuong == null ? 0 : p.quangDuong);
            totalSeconds += (p.thoiGianGiay == null ? 0 : p.thoiGianGiay);
        }

        long avg = points.isEmpty() ? 0 : Math.round(totalSteps / (double) points.size());

        txtTotalSteps.setText(String.valueOf(totalSteps));
        txtAverage.setText(String.valueOf(avg));

        txtSmallSteps.setText(totalSteps + "\nbước");
        txtSmallCalories.setText("0\nkcal");
        txtSmallDistance.setText(StepsFormat.formatKmFromMeters((float) totalMeters));
        txtSmallTime.setText(StepsFormat.formatMinutesFromSeconds((int) totalSeconds));
    }

    private void renderCalendar() {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        txtWeekRange.setText(String.format(Locale.getDefault(), "Tháng %d %d", (month + 1), year));

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
        int startDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 1
        if (startDayOfWeek == 0) startDayOfWeek = 7; // Monday = 1

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

                dayView.setText(String.valueOf(day));

                Calendar now = Calendar.getInstance();
                int today = now.get(Calendar.DAY_OF_MONTH);
                int thisMonth = now.get(Calendar.MONTH);
                int thisYear = now.get(Calendar.YEAR);

                if (day == today && month == thisMonth && year == thisYear) {
                    dayView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_today));
                    dayView.setTypeface(Typeface.DEFAULT_BOLD);
                }

                dayView.setOnClickListener(v -> onDaySelected((TextView) v));

                row.addView(dayView);
                day++;
            }
        }
    }

    private void onDaySelected(TextView dayView) {
        if (dayView.getText() == null || dayView.getText().toString().trim().isEmpty()) return;

        if (selectedDayView != null) {
            selectedDayView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_normal));
            selectedDayView.setTextColor(Color.BLACK);
            selectedDayView.setTypeface(Typeface.DEFAULT);
        }

        dayView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_selected));
        dayView.setTextColor(Color.WHITE);
        dayView.setTypeface(Typeface.DEFAULT_BOLD);
        selectedDayView = dayView;

        try {
            int d = Integer.parseInt(dayView.getText().toString());
            BuocChanNgayPoint p = dayToPoint.get(d);
            if (p != null) {
                txtSmallSteps.setText(StepsFormat.formatStepsTile(p.soBuoc));
                txtSmallCalories.setText("0\nkcal");
                txtSmallDistance.setText(StepsFormat.formatKmFromMeters(p.quangDuong == null ? 0f : p.quangDuong));
                txtSmallTime.setText(StepsFormat.formatMinutesFromSeconds(p.thoiGianGiay == null ? 0 : p.thoiGianGiay));
            }
        } catch (Exception ignored) {}
    }
}
