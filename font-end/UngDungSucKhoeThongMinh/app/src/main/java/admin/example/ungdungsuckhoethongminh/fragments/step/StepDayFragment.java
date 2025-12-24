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

    private ImageView imgCenterIcon;
    private TextView txtSmallSteps, txtSmallCalories, txtSmallDistance, txtSmallTime;
    private TextView txtCenterTop, txtCenterBottom, txtDayInfo;
    private ImageButton btnPrevDay, btnNextDay;

    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private final StepsRepository repository = new StepsRepository();

    public StepDayFragment() {}

    private int getTaiKhoanId() {
        // TODO: wire real account id when login/account flow is available
        Bundle args = getArguments();
        if (args != null && args.containsKey("idTaiKhoan")) {
            return args.getInt("idTaiKhoan", 1);
        }
        // fallback to session
        return admin.example.ungdungsuckhoethongminh.steps.session.StepsUserSession.getIdTaiKhoan(requireContext());
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
            txtCenterBottom.setText("bước");
            imgCenterIcon.setImageResource(R.drawable.buocchan);
        });
        txtSmallCalories.setOnClickListener(v -> {
            txtCenterBottom.setText("kcal");
            imgCenterIcon.setImageResource(R.drawable.mdi_fire);
        });
        txtSmallDistance.setOnClickListener(v -> {
            txtCenterBottom.setText("quãng đường");
            imgCenterIcon.setImageResource(R.drawable.arrow_right);
        });
        txtSmallTime.setOnClickListener(v -> {
            txtCenterBottom.setText("thời gian");
            imgCenterIcon.setImageResource(R.drawable.mdi_light_clock);
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
                BuocChanNgayPoint p = (data != null && !data.isEmpty()) ? data.get(0) : null;
                renderPoint(p);
            }

            @Override
            public void onError(@NonNull Throwable t) {
                if (!isAdded()) return;
                renderPoint(null);
            }
        });
    }

    private void renderPoint(@Nullable BuocChanNgayPoint p) {
        int steps = (p == null || p.soBuoc == null) ? 0 : p.soBuoc;
        float meters = (p == null || p.quangDuong == null) ? 0f : p.quangDuong;
        int seconds = (p == null || p.thoiGianGiay == null) ? 0 : p.thoiGianGiay;

        txtSmallSteps.setText(StepsFormat.formatStepsTile(steps));
        // Backend buocchan currently doesn't provide calories
        txtSmallCalories.setText("0\nkcal");
        txtSmallDistance.setText(StepsFormat.formatKmFromMeters(meters));
        txtSmallTime.setText(StepsFormat.formatMinutesFromSeconds(seconds));

        // Center defaults to steps
        txtCenterTop.setText(String.valueOf(steps));
        txtCenterBottom.setText("bước");
        imgCenterIcon.setImageResource(R.drawable.buocchan);
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
