package admin.example.ungdungsuckhoethongminh.fragments.sleep;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeActivity;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeGetBedActivity;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeGetUpActivity;
import admin.example.ungdungsuckhoethongminh.sleep.model.SleepNgayPoint;
import admin.example.ungdungsuckhoethongminh.sleep.repository.SleepRepository;
import admin.example.ungdungsuckhoethongminh.sleep.util.SleepFormat;
import admin.example.ungdungsuckhoethongminh.sleep.util.SleepUserResolver;

public class SleepWeekFragment extends Fragment {

    private ImageButton btnPrevDay, btnNextDay;
    private TextView txtDayInfo;
    private TextView txtTotalSteps;

    private TextView txtSleepDuration;
    private TextView txtBedtime;
    private TextView txtWakeTime;
    private TextView txtSleepQuality;

    private final Calendar anchorDay = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private final SleepRepository repository = new SleepRepository();

    public SleepWeekFragment() {}

    private int getTaiKhoanId() {
        return SleepUserResolver.resolveIdTaiKhoan(requireContext(), getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sleep_week, container, false);

        btnPrevDay = view.findViewById(R.id.btnPrevDay);
        btnNextDay = view.findViewById(R.id.btnNextDay);
        txtDayInfo = view.findViewById(R.id.txtDayInfo);
        txtTotalSteps = view.findViewById(R.id.txtTotalSteps); // UI uses this as percent label

        txtSleepDuration = view.findViewById(R.id.txtSleepDuration);
        txtBedtime = view.findViewById(R.id.txtBedtime);
        txtWakeTime = view.findViewById(R.id.txtWakeTime);
        txtSleepQuality = view.findViewById(R.id.txtSleepQuality);

        txtDayInfo.setText("Tuần này");

        btnPrevDay.setOnClickListener(v -> {
            anchorDay.add(Calendar.DAY_OF_MONTH, -7);
            fetchAndRender();
        });

        btnNextDay.setOnClickListener(v -> {
            anchorDay.add(Calendar.DAY_OF_MONTH, 7);
            fetchAndRender();
        });

        // Detail screens
        LinearLayout cardSleep = view.findViewById(R.id.cardSleepDuration);
        cardSleep.setOnClickListener(v -> startActivity(new Intent(getActivity(), SleepTimeActivity.class)));

        LinearLayout cardSleepBed = view.findViewById(R.id.cardSleepBed);
        cardSleepBed.setOnClickListener(v -> startActivity(new Intent(getActivity(), SleepTimeGetBedActivity.class)));

        LinearLayout cardSleepUp = view.findViewById(R.id.cardSleepUp);
        cardSleepUp.setOnClickListener(v -> startActivity(new Intent(getActivity(), SleepTimeGetUpActivity.class)));

        fetchAndRender();
        return view;
    }

    private void fetchAndRender() {
        String ngay = sdf.format(anchorDay.getTime());

        repository.getTuan(getTaiKhoanId(), ngay, new SleepRepository.ResultCallback<List<SleepNgayPoint>>() {
            @Override
            public void onSuccess(@NonNull List<SleepNgayPoint> data) {
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

    private void renderWeek(@NonNull List<SleepNgayPoint> points) {
        int total = 0;
        int max = 0;
        for (SleepNgayPoint p : points) {
            int m = SleepFormat.toMinutes(p == null ? null : p.tongThoiGianNgu);
            total += m;
            if (m > max) max = m;
        }

        int avgMinutes = points.isEmpty() ? 0 : Math.round(total / (float) points.size());
        int pct = SleepFormat.recoveryPercentFromDuration(avgMinutes);

        if (txtTotalSteps != null) txtTotalSteps.setText(String.valueOf(pct));
        if (txtSleepDuration != null) txtSleepDuration.setText(SleepFormat.formatDuration(avgMinutes));

        admin.example.ungdungsuckhoethongminh.sleep.util.SleepQuality q = SleepFormat.qualityFromDuration(avgMinutes);
        if (txtSleepQuality != null) {
            txtSleepQuality.setText(q.label);
            txtSleepQuality.setTextColor(q.color);
        }

        // Average bedtime/wake time from daily points
        List<String> bedTimes = new ArrayList<>();
        List<String> wakeTimes = new ArrayList<>();
        for (SleepNgayPoint p : points) {
            if (p != null) {
                if (p.gioBatDau != null) bedTimes.add(p.gioBatDau);
                if (p.gioKetThuc != null) wakeTimes.add(p.gioKetThuc);
            }
        }
        String bedAvg = SleepFormat.averageTime(bedTimes);
        String wakeAvg = SleepFormat.averageTime(wakeTimes);

        if (txtBedtime != null) txtBedtime.setText(SleepFormat.safeTime(bedAvg));
        if (txtWakeTime != null) txtWakeTime.setText(SleepFormat.safeTime(wakeAvg));

        for (int i = 1; i <= 7; i++) {
            int idx = i - 1;
            int m = (idx < points.size()) ? SleepFormat.toMinutes(points.get(idx) == null ? null : points.get(idx).tongThoiGianNgu) : 0;
            setBar(i, m, max);
        }
    }

    private void setBar(int barIndex, int minutes, int maxMinutes) {
        if (getView() == null) return;

        int barId = getResources().getIdentifier("bar" + barIndex, "id", requireContext().getPackageName());
        int valueId = getResources().getIdentifier("bar" + barIndex + "Value", "id", requireContext().getPackageName());

        View bar = getView().findViewById(barId);
        TextView value = getView().findViewById(valueId);

        if (value != null) value.setText(SleepFormat.formatHours1dp(minutes));

        if (bar != null) {
            ViewGroup.LayoutParams lp = bar.getLayoutParams();
            int base = dp(8);
            int maxH = dp(120);
            int h = (maxMinutes <= 0) ? base : base + Math.round((minutes / (float) maxMinutes) * maxH);
            lp.height = h;
            bar.setLayoutParams(lp);
        }
    }

    private int dp(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
