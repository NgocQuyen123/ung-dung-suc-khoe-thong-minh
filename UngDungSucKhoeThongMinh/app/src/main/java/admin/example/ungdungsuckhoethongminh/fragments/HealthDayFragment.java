package admin.example.ungdungsuckhoethongminh.fragments;

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

public class HealthDayFragment extends Fragment {
    private ImageView imgCircle, imgCenterIcon;
    private TextView txtSmallSteps, txtSmallCalories, txtSmallDistance, txtSmallTime;
    private TextView txtCenterTop, txtCenterBottom, txtDayInfo;
    private ImageButton btnPrevDay, btnNextDay;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public HealthDayFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_day, container, false);

        // Find views
        imgCircle = view.findViewById(R.id.imgCircle);
        imgCenterIcon = view.findViewById(R.id.imgCenterIcon);
        txtCenterTop = view.findViewById(R.id.txtCenterTop);
        txtCenterBottom = view.findViewById(R.id.txtCenterBottom);

        txtDayInfo = view.findViewById(R.id.txtDayInfo);

        btnPrevDay = view.findViewById(R.id.btnPrevDay);
        btnNextDay = view.findViewById(R.id.btnNextDay);

        return view;
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
