package admin.example.ungdungsuckhoethongminh.fragments.step;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.steps.model.BuocChanThangPoint;
import admin.example.ungdungsuckhoethongminh.steps.repository.StepsRepository;
import admin.example.ungdungsuckhoethongminh.steps.util.StepsFormat;

public class StepYearFragment extends Fragment {

    private LinearLayout columnContainer;
    private ImageButton btnPrevDay, btnNextDay;
    private TextView txtDayInfo, txtWeekRange, txtTotalSteps, txtAverage;
    private TextView txtSmallSteps, txtSmallCalories, txtSmallDistance, txtSmallTime;

    private final Calendar calendar = Calendar.getInstance();
    private final StepsRepository repository = new StepsRepository();

    public StepYearFragment() {}

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

        View view = inflater.inflate(R.layout.fragment_step_year, container, false);

        columnContainer = view.findViewById(R.id.columnContainer);
        btnPrevDay = view.findViewById(R.id.btnPrevDay);
        btnNextDay = view.findViewById(R.id.btnNextDay);
        txtDayInfo = view.findViewById(R.id.txtDayInfo);
        txtWeekRange = view.findViewById(R.id.txtWeekRange);
        txtTotalSteps = view.findViewById(R.id.txtTotalSteps);
        txtAverage = view.findViewById(R.id.txtAverage);

        txtSmallSteps = view.findViewById(R.id.txtSmallSteps);
        txtSmallCalories = view.findViewById(R.id.txtSmallCalories);
        txtSmallDistance = view.findViewById(R.id.txtSmallDistance);
        txtSmallTime = view.findViewById(R.id.txtSmallTime);

        txtDayInfo.setText("Năm");

        btnPrevDay.setOnClickListener(v -> {
            calendar.add(Calendar.YEAR, -1);
            fetchAndRender();
        });

        btnNextDay.setOnClickListener(v -> {
            calendar.add(Calendar.YEAR, 1);
            fetchAndRender();
        });

        fetchAndRender();
        return view;
    }

    private void fetchAndRender() {
        int year = calendar.get(Calendar.YEAR);
        txtWeekRange.setText(String.valueOf(year));

        repository.getNam(getTaiKhoanId(), year, new StepsRepository.ResultCallback<List<BuocChanThangPoint>>() {
            @Override
            public void onSuccess(@NonNull List<BuocChanThangPoint> data) {
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

    private void renderYear(@NonNull List<BuocChanThangPoint> months) {
        long totalSteps = 0;
        double totalMeters = 0;
        long totalSeconds = 0;

        long maxSteps = 0;
        for (BuocChanThangPoint m : months) {
            if (m == null) continue;
            long s = (m.soBuoc == null ? 0 : m.soBuoc);
            totalSteps += s;
            totalMeters += (m.quangDuong == null ? 0 : m.quangDuong);
            totalSeconds += (m.thoiGianGiay == null ? 0 : m.thoiGianGiay);
            if (s > maxSteps) maxSteps = s;
        }

        long avg = months.isEmpty() ? 0 : Math.round(totalSteps / (double) months.size());

        txtTotalSteps.setText(String.valueOf(totalSteps));
        txtAverage.setText(String.valueOf(avg));

        txtSmallSteps.setText(totalSteps + "\nbước");
        long totalKcal = 0;
        for (BuocChanThangPoint m : months) {
            if (m != null && m.kcal != null) totalKcal += Math.round(m.kcal);
        }
        txtSmallCalories.setText(totalKcal + "\nkcal");
        txtSmallDistance.setText(StepsFormat.formatKmFromMeters((float) totalMeters));
        txtSmallTime.setText(StepsFormat.formatMinutesFromSeconds((int) totalSeconds));

        columnContainer.removeAllViews();
        long finalMaxSteps = maxSteps;
        columnContainer.post(() -> generateColumns(months, finalMaxSteps));
    }

    private void generateColumns(@NonNull List<BuocChanThangPoint> months, long maxSteps) {
        if (getContext() == null) return;

        int count = 12;
        int containerWidth = columnContainer.getWidth();
        int columnWidth = (count == 0) ? containerWidth : (containerWidth / count);

        long[] stepsByMonth = new long[12];
        for (BuocChanThangPoint m : months) {
            if (m == null || m.thang == null) continue;
            int idx = m.thang - 1;
            if (idx < 0 || idx >= 12) continue;
            stepsByMonth[idx] = (m.soBuoc == null ? 0 : m.soBuoc);
        }

        for (int i = 0; i < count; i++) {
            long steps = stepsByMonth[i];

            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER_HORIZONTAL);

            LinearLayout.LayoutParams colParams = new LinearLayout.LayoutParams(
                    columnWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            col.setLayoutParams(colParams);

            TextView number = new TextView(getContext());
            number.setText(String.valueOf(i + 1));
            number.setTextSize(18);
            number.setTypeface(Typeface.DEFAULT_BOLD);
            number.setGravity(Gravity.CENTER);
            number.setPadding(0, 0, 0, dp(10));

            View line = new View(getContext());
            int baseH = dp(8);
            int maxH = dp(140);
            int h = (maxSteps <= 0) ? baseH : baseH + Math.round((steps / (float) maxSteps) * maxH);

            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(dp(2), h);
            lineParams.weight = 0f;
            line.setLayoutParams(lineParams);
            line.setBackgroundColor(Color.parseColor("#BDBDBD"));

            col.addView(number);
            col.addView(line);

            columnContainer.addView(col);
        }
    }

    private int dp(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
