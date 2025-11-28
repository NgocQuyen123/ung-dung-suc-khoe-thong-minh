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
import java.util.Locale;

import admin.example.ungdungsuckhoethongminh.R;

public class StepDayFragment extends Fragment {

    private ImageView imgCircle, imgCenterIcon;
    private TextView txtSmallSteps, txtSmallCalories, txtSmallDistance, txtSmallTime;
    private TextView txtCenterTop, txtCenterBottom, txtDayInfo;
    private ImageButton btnPrevDay, btnNextDay;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public StepDayFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_day, container, false);

        // Find views
        imgCircle = view.findViewById(R.id.imgCircle);
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

        loadDemoData();
        updateDayInfo();

        btnPrevDay.setOnClickListener(v -> {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            loadDemoData();
            updateDayInfo();
        });

        btnNextDay.setOnClickListener(v -> {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            loadDemoData();
            updateDayInfo();
        });

//         Click vào ô nhỏ để đổi nội dung giữa vòng tròn
        txtSmallSteps.setOnClickListener(v -> {
            txtCenterTop.setText("6500");
            txtCenterBottom.setText("bước");
            imgCenterIcon.setImageResource(R.drawable.buocchan);
        });

        txtSmallCalories.setOnClickListener(v -> {
            txtCenterTop.setText("320");
            txtCenterBottom.setText("kcal");
            imgCenterIcon.setImageResource(R.drawable.mdi_fire);
        });

        txtSmallDistance.setOnClickListener(v -> {
            txtCenterTop.setText("4500");
            txtCenterBottom.setText("m");
            imgCenterIcon.setImageResource(R.drawable.arrow_right);
        });

        txtSmallTime.setOnClickListener(v -> {
            txtCenterTop.setText("35");
            txtCenterBottom.setText("phút");
            imgCenterIcon.setImageResource(R.drawable.mdi_light_clock);
        });btnPrevDay.setOnClickListener(v -> {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            loadDemoData();
            updateDayInfo();
        });

        btnNextDay.setOnClickListener(v -> {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            loadDemoData();
            updateDayInfo();
        });

//         Click vào ô nhỏ để đổi nội dung giữa vòng tròn
        txtSmallSteps.setOnClickListener(v -> {
            txtCenterTop.setText("6500");
            txtCenterBottom.setText("bước");
            imgCenterIcon.setImageResource(R.drawable.buocchan);
        });

        txtSmallCalories.setOnClickListener(v -> {
            txtCenterTop.setText("320");
            txtCenterBottom.setText("kcal");
            imgCenterIcon.setImageResource(R.drawable.mdi_fire);
        });

        txtSmallDistance.setOnClickListener(v -> {
            txtCenterTop.setText("4500");
            txtCenterBottom.setText("m");
            imgCenterIcon.setImageResource(R.drawable.arrow_right);
        });

        txtSmallTime.setOnClickListener(v -> {
            txtCenterTop.setText("35");
            txtCenterBottom.setText("phút");
            imgCenterIcon.setImageResource(R.drawable.mdi_light_clock);
        });

        return view;
    }

    // FIX CỨNG DỮ LIỆU DEMO  -------------------------
    private void loadDemoData() {

        txtSmallSteps.setText("6500\nbước");
        txtSmallCalories.setText("320\nkcal");
        txtSmallDistance.setText("4500\nm");
        txtSmallTime.setText("35\nphút");

        txtCenterTop.setText("6500");
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
