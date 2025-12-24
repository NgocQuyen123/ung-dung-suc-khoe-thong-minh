package admin.example.ungdungsuckhoethongminh.fragments.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.steps.model.BuocChanNgayPoint;
import admin.example.ungdungsuckhoethongminh.steps.repository.StepsRepository;
import admin.example.ungdungsuckhoethongminh.steps.util.StepsFormat;

public class StepDayFragment extends Fragment {

    private enum CenterMetric {
        STEPS,
        KCAL,
        DISTANCE,
        TIME
    }

    private ImageView imgCenterIcon;
    private TextView txtSmallSteps, txtSmallCalories, txtSmallDistance, txtSmallTime;
    private TextView txtCenterTop, txtCenterBottom, txtDayInfo;
    private ImageButton btnPrevDay, btnNextDay;

    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private final StepsRepository repository = new StepsRepository();

    private @Nullable BuocChanNgayPoint currentPoint;
    private CenterMetric selectedMetric = CenterMetric.STEPS;

    public StepDayFragment() {}

    private int getTaiKhoanId() {
        return admin.example.ungdungsuckhoethongminh.steps.util.StepsUserResolver
                .resolveIdTaiKhoan(requireContext(), getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_day, container, false);

        imgCenterIcon = view.findViewById(R.id.imgCenterIcon);
        txtCenterTop = view.findViewById(R.id.txtCenterTop);
        txtCenterBottom = view.findViewById(R.id.txtCenterBottom);

        txtSmallSteps = view.findViewById(R.id.txtSmallSteps);
        txtSmallCalories = view.findViewById(R.id.txtSmallCalories);
        txtSmallDistance = view.findViewById(R.id.txtSmallDistance);
        txtSmallTime = view.findViewById(R.id.txtSmallTime);
        txtDayInfo = view.findViewById(R.id.txtDayInfo);

        btnPrevDay = view.findViewById(R.id.btnPrevDay);
        btnNextDay = view.findViewById(R.id.btnNextDay);

        btnPrevDay.setOnClickListener(v -> {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            updateDayInfo();
            fetchAndRender();
        });
        btnNextDay.setOnClickListener(v -> {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            updateDayInfo();
            fetchAndRender();
        });

        // Click tiles -> show in center
        txtSmallSteps.setOnClickListener(v -> {
            selectedMetric = CenterMetric.STEPS;
            showCenter(selectedMetric);
        });
        txtSmallCalories.setOnClickListener(v -> {
            selectedMetric = CenterMetric.KCAL;
            showCenter(selectedMetric);
        });
        txtSmallDistance.setOnClickListener(v -> {
            selectedMetric = CenterMetric.DISTANCE;
            showCenter(selectedMetric);
        });
        txtSmallTime.setOnClickListener(v -> {
            selectedMetric = CenterMetric.TIME;
            showCenter(selectedMetric);
        });

        updateDayInfo();
        fetchAndRender();

        return view;
    }

    private void fetchAndRender() {
        String ngay = sdf.format(calendar.getTime());
        int idTaiKhoan = getTaiKhoanId();

        // simple loading
        txtSmallSteps.setText("...\nbước");
        txtSmallCalories.setText("...\nkcal");
        txtSmallDistance.setText("...\n...");
        txtSmallTime.setText("...\nphút");

        repository.getNgay(idTaiKhoan, ngay, new StepsRepository.ResultCallback<List<BuocChanNgayPoint>>() {
            @Override
            public void onSuccess(@NonNull List<BuocChanNgayPoint> data) {
                if (!isAdded()) return;
                currentPoint = (data != null && !data.isEmpty()) ? data.get(0) : null;
                renderTiles(currentPoint);
                showCenter(selectedMetric);
            }

            @Override
            public void onError(@NonNull Throwable t) {
                if (!isAdded()) return;
                currentPoint = null;
                renderTiles(null);
                showCenter(selectedMetric);
            }
        });
    }

    private void renderTiles(@Nullable BuocChanNgayPoint p) {
        int steps = (p == null || p.soBuoc == null) ? 0 : p.soBuoc;
        float meters = (p == null || p.quangDuong == null) ? 0f : p.quangDuong;
        int seconds = (p == null || p.thoiGianGiay == null) ? 0 : p.thoiGianGiay;
        float kcal = (p == null || p.kcal == null) ? 0f : p.kcal;

        txtSmallSteps.setText(StepsFormat.formatStepsTile(steps));
        txtSmallCalories.setText(StepsFormat.formatKcalTile(kcal));
        txtSmallDistance.setText(StepsFormat.formatKmFromMeters(meters));
        txtSmallTime.setText(StepsFormat.formatMinutesFromSeconds(seconds));
    }

    private void showCenter(@NonNull CenterMetric metric) {
        BuocChanNgayPoint p = currentPoint;

        int steps = (p == null || p.soBuoc == null) ? 0 : p.soBuoc;
        float meters = (p == null || p.quangDuong == null) ? 0f : p.quangDuong;
        int seconds = (p == null || p.thoiGianGiay == null) ? 0 : p.thoiGianGiay;
        float kcal = (p == null || p.kcal == null) ? 0f : p.kcal;

        switch (metric) {
            case KCAL:
                txtCenterTop.setText(String.format(Locale.getDefault(), "%.0f", kcal));
                txtCenterBottom.setText("kcal");
                imgCenterIcon.setImageResource(R.drawable.mdi_fire);
                break;
            case DISTANCE:
                // show as meters or km (without unit duplication from tile)
                if (meters < 1000f) {
                    txtCenterTop.setText(String.format(Locale.getDefault(), "%.0f", meters));
                    txtCenterBottom.setText("m");
                } else {
                    txtCenterTop.setText(String.format(Locale.getDefault(), "%.1f", (meters / 1000f)));
                    txtCenterBottom.setText("km");
                }
                imgCenterIcon.setImageResource(R.drawable.arrow_right);
                break;
            case TIME:
                int minutes = Math.round(seconds / 60f);
                txtCenterTop.setText(String.valueOf(minutes));
                txtCenterBottom.setText("phút");
                imgCenterIcon.setImageResource(R.drawable.mdi_light_clock);
                break;
            case STEPS:
            default:
                txtCenterTop.setText(String.valueOf(steps));
                txtCenterBottom.setText("bước");
                imgCenterIcon.setImageResource(R.drawable.buocchan);
                break;
        }
    }

    private void updateDayInfo() {
        Calendar today = Calendar.getInstance();
        long diffMillis = today.getTimeInMillis() - calendar.getTimeInMillis();
        int diffDays = (int) (diffMillis / (1000 * 60 * 60 * 24));

        if (diffDays == 0) {
            txtDayInfo.setText("Hôm nay");
        } else if (diffDays == 1) {
            txtDayInfo.setText("Hôm qua");
        } else if (diffDays > 1) {
            txtDayInfo.setText(diffDays + " ngày trước");
        } else {
            txtDayInfo.setText("Ngày sau");
        }
    }
}
