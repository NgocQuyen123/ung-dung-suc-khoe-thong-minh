package admin.example.ungdungsuckhoethongminh.fragments.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.steps.model.BuocChanNgayPoint;
import admin.example.ungdungsuckhoethongminh.steps.repository.StepsRepository;
import admin.example.ungdungsuckhoethongminh.steps.util.StepsFormat;

public class StepWeekFragment extends Fragment {

    private ImageButton btnPrevDay, btnNextDay;
    private TextView txtDayInfo, txtWeekRange, txtTotalSteps, txtAverage;
    private TextView txtSmallSteps, txtSmallCalories, txtSmallDistance, txtSmallTime;

    private final Calendar anchorDay = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final SimpleDateFormat sdfShort = new SimpleDateFormat("dd/MM", Locale.getDefault());

    private final StepsRepository repository = new StepsRepository();

    public StepWeekFragment() {}

    private int getTaiKhoanId() {
        return admin.example.ungdungsuckhoethongminh.steps.util.StepsUserResolver
                .resolveIdTaiKhoan(requireContext(), getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_week, container, false);

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

        txtDayInfo.setText("Tuần này");

        btnPrevDay.setOnClickListener(v -> {
            anchorDay.add(Calendar.DAY_OF_MONTH, -7);
            fetchAndRender();
        });

        btnNextDay.setOnClickListener(v -> {
            anchorDay.add(Calendar.DAY_OF_MONTH, 7);
            fetchAndRender();
        });

        fetchAndRender();
        return view;
    }

    private void fetchAndRender() {
        String ngay = sdf.format(anchorDay.getTime());

        repository.getTuan(getTaiKhoanId(), ngay, new StepsRepository.ResultCallback<List<BuocChanNgayPoint>>() {
            @Override
            public void onSuccess(@NonNull List<BuocChanNgayPoint> data) {
                if (!isAdded()) return;
                renderWeek(data);
            }

            @Override
            public void onError(@NonNull Throwable t) {
                if (!isAdded()) return;
                renderWeek(new ArrayList<>());
            }
        });
    }

    private void renderWeek(@NonNull List<BuocChanNgayPoint> points) {
        admin.example.ungdungsuckhoethongminh.steps.util.StepsStats.DayStats s =
                admin.example.ungdungsuckhoethongminh.steps.util.StepsStats.sumDays(points);

        txtTotalSteps.setText(String.valueOf(s.totalSteps));
        txtAverage.setText(String.valueOf(s.avgSteps(points.size())));

        Calendar start = (Calendar) anchorDay.clone();
        start.setFirstDayOfWeek(Calendar.MONDAY);
        start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Calendar end = (Calendar) start.clone();
        end.add(Calendar.DAY_OF_MONTH, 6);
        txtWeekRange.setText(sdfShort.format(start.getTime()) + " - " + sdfShort.format(end.getTime()));

        txtSmallSteps.setText(s.totalSteps + "\nbước");
        txtSmallCalories.setText(s.totalKcal + "\nkcal");
        txtSmallDistance.setText(StepsFormat.formatKmFromMeters((float) s.totalMeters));
        txtSmallTime.setText(StepsFormat.formatMinutesFromSeconds((int) s.totalSeconds));

        int maxSteps = 0;
        for (BuocChanNgayPoint p : points) {
            int stepsVal = (p == null || p.soBuoc == null) ? 0 : p.soBuoc;
            if (stepsVal > maxSteps) maxSteps = stepsVal;
        }

        for (int i = 1; i <= 7; i++) {
            int idx = i - 1;
            int steps = (idx < points.size() && points.get(idx) != null && points.get(idx).soBuoc != null)
                    ? points.get(idx).soBuoc : 0;
            setBar(i, steps, maxSteps);
        }
    }

    private void setBar(int barIndex, int steps, int maxSteps) {
        if (getView() == null) return;

        int barId = getResources().getIdentifier("bar" + barIndex, "id", requireContext().getPackageName());
        int valueId = getResources().getIdentifier("bar" + barIndex + "Value", "id", requireContext().getPackageName());

        View bar = getView().findViewById(barId);
        TextView value = getView().findViewById(valueId);

        if (value != null) value.setText(String.valueOf(steps));

        if (bar != null) {
            ViewGroup.LayoutParams lp = bar.getLayoutParams();
            int base = dp(8);
            int maxH = dp(120);
            int h = (maxSteps <= 0) ? base : base + Math.round((steps / (float) maxSteps) * maxH);
            lp.height = h;
            bar.setLayoutParams(lp);
        }
    }

    private int dp(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
