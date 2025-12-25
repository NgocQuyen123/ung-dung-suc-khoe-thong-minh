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
import admin.example.ungdungsuckhoethongminh.sleep.util.SleepQuality;
import admin.example.ungdungsuckhoethongminh.sleep.util.SleepUserResolver;
import admin.example.ungdungsuckhoethongminh.view.HalfCircleView;

public class SleepDayFragment extends Fragment {

    private ImageButton btnPrevDay, btnNextDay;
    private TextView txtDayInfo;

    private HalfCircleView recoveryArc;
    private TextView txtRecoveryPercent;
    private TextView txtRecoveryStatus;

    private TextView txtSleepDuration;
    private TextView txtBedtime;
    private TextView txtWakeTime;
    private TextView txtSleepQuality;

    private final Calendar day = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private final SleepRepository repository = new SleepRepository();

    private int getTaiKhoanId() {
        return SleepUserResolver.resolveIdTaiKhoan(requireContext(), getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sleep_day, container, false);

        btnPrevDay = view.findViewById(R.id.btnPrevDay);
        btnNextDay = view.findViewById(R.id.btnNextDay);
        txtDayInfo = view.findViewById(R.id.txtDayInfo);

        recoveryArc = view.findViewById(R.id.recoveryArc);
        txtRecoveryPercent = view.findViewById(R.id.txtRecoveryPercent);
        txtRecoveryStatus = view.findViewById(R.id.txtRecoveryStatus);

        txtSleepDuration = view.findViewById(R.id.txtSleepDuration);
        txtBedtime = view.findViewById(R.id.txtBedtime);
        txtWakeTime = view.findViewById(R.id.txtWakeTime);
        txtSleepQuality = view.findViewById(R.id.txtSleepQuality);

        txtDayInfo.setText("Hôm nay");

        btnPrevDay.setOnClickListener(v -> {
            day.add(Calendar.DAY_OF_MONTH, -1);
            fetchAndRender();
        });

        btnNextDay.setOnClickListener(v -> {
            day.add(Calendar.DAY_OF_MONTH, 1);
            fetchAndRender();
        });

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
        String ngay = sdf.format(day.getTime());
        repository.getNgay(getTaiKhoanId(), ngay, new SleepRepository.ResultCallback<List<SleepNgayPoint>>() {
            @Override
            public void onSuccess(@NonNull List<SleepNgayPoint> data) {
                if (!isAdded()) return;
                renderDay(data.isEmpty() ? null : data.get(0));
            }

            @Override
            public void onError(@NonNull Throwable t) {
                if (!isAdded()) return;
                renderDay(null);
            }
        });
    }

    private void renderDay(@Nullable SleepNgayPoint p) {
        Integer dur = (p == null) ? null : p.tongThoiGianNgu;
        int pct = SleepFormat.recoveryPercentFromDuration(dur);

        if (recoveryArc != null) recoveryArc.setProgress(pct);
        if (txtRecoveryPercent != null) txtRecoveryPercent.setText(pct + "%");

        SleepQuality q = SleepFormat.qualityFromDuration(dur);
        if (txtRecoveryStatus != null) {
            txtRecoveryStatus.setText(q.label);
            txtRecoveryStatus.setTextColor(q.color);
        }

        if (txtSleepDuration != null) txtSleepDuration.setText(SleepFormat.formatDuration(dur));
        if (txtBedtime != null) txtBedtime.setText(SleepFormat.safeTime(p == null ? null : p.gioBatDau));
        if (txtWakeTime != null) txtWakeTime.setText(SleepFormat.safeTime(p == null ? null : p.gioKetThuc));

        if (txtSleepQuality != null) {
            txtSleepQuality.setText(q.label);
            txtSleepQuality.setTextColor(q.color);
        }
    }
}
