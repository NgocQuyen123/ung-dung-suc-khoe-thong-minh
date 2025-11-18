package admin.example.ungdungsuckhoethongminh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import admin.example.ungdungsuckhoethongminh.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeekFragment extends Fragment {

    TextView txtTotalSteps, txtAverage, txtWeekRange;
    TextView infoSteps, infoCalories, infoDistance, infoTime;

    View[] bars = new View[7];
    TextView[] barValues = new TextView[7];

    ImageButton btnPrev, btnNext;

    ApiService api;

    String currentWeek; // VD: 2025-W38

    TextView txtDayInfo;

    public WeekFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_week, container, false);
        api = ApiClient.getApiService();

        bindViews(view);

        // Lấy tuần hiện tại
        currentWeek = getCurrentWeekString();

        // Load API tuần hiện tại
        loadFitnessWeek(currentWeek);

        // Sự kiện nút
        handleButtons();

        return view;
    }

    private void handleButtons() {
        btnPrev.setOnClickListener(v -> {
            currentWeek = changeWeek(currentWeek, -1);  // Lùi 1 tuần
            loadFitnessWeek(currentWeek);
        });

        btnNext.setOnClickListener(v -> {
            currentWeek = changeWeek(currentWeek, +1);  // Tăng 1 tuần
            loadFitnessWeek(currentWeek);
        });
    }

    private String getCurrentWeekString() {
        LocalDate now = LocalDate.now();
        WeekFields wf = WeekFields.of(Locale.getDefault());

        int year = now.getYear();
        int week = now.get(wf.weekOfWeekBasedYear());

        return year + "-W" + week;
    }

    private String changeWeek(String weekString, int offset) {
        // weekString: "2025-W38"
        String[] arr = weekString.split("-W");
        int year = Integer.parseInt(arr[0]);
        int week = Integer.parseInt(arr[1]);

        week += offset;

        if (week <= 0) {
            year -= 1;
            week = 52;
        } else if (week > 52) {
            year += 1;
            week = 1;
        }

        return year + "-W" + week;
    }

    private void bindViews(View v) {
        txtTotalSteps = v.findViewById(R.id.txtTotalSteps);
        txtAverage = v.findViewById(R.id.txtAverage);
        txtWeekRange = v.findViewById(R.id.txtWeekRange);

        infoSteps = v.findViewById(R.id.infoSteps);
        infoCalories = v.findViewById(R.id.infoCalories);
        infoDistance = v.findViewById(R.id.infoDistance);
        infoTime = v.findViewById(R.id.infoTime);

        btnPrev = v.findViewById(R.id.btnPrevDay);
        btnNext = v.findViewById(R.id.btnNextDay);
        txtDayInfo = v.findViewById(R.id.txtDayInfo);

        bars[0] = v.findViewById(R.id.bar1);
        bars[1] = v.findViewById(R.id.bar2);
        bars[2] = v.findViewById(R.id.bar3);
        bars[3] = v.findViewById(R.id.bar4);
        bars[4] = v.findViewById(R.id.bar5);
        bars[5] = v.findViewById(R.id.bar6);
        bars[6] = v.findViewById(R.id.bar7);

        barValues[0] = v.findViewById(R.id.bar1Value);
        barValues[1] = v.findViewById(R.id.bar2Value);
        barValues[2] = v.findViewById(R.id.bar3Value);
        barValues[3] = v.findViewById(R.id.bar4Value);
        barValues[4] = v.findViewById(R.id.bar5Value);
        barValues[5] = v.findViewById(R.id.bar6Value);
        barValues[6] = v.findViewById(R.id.bar7Value);
    }

    private void loadFitnessWeek(String weekId) {

        // Cập nhật text giữa theo tuần
        txtDayInfo.setText(getWeekLabel(weekId));

        api.getFitnessWeekByWeek(weekId).enqueue(new Callback<List<FitnessWeek>>() {
            @Override
            public void onResponse(Call<List<FitnessWeek>> call, Response<List<FitnessWeek>> response) {

                if (!response.isSuccessful() || response.body() == null || response.body().isEmpty()) {
                    Toast.makeText(getContext(), "Không có dữ liệu tuần này", Toast.LENGTH_SHORT).show();

                    // Reset dữ liệu về 0
                    txtTotalSteps.setText("0");
                    txtAverage.setText("Trung bình 0");
                    txtWeekRange.setText("-");
                    infoSteps.setText("0\nbước");
                    infoCalories.setText("0\nkcal");
                    infoDistance.setText("0\nm");
                    infoTime.setText("0\nphút");

                    // Reset biểu đồ cột
                    for (int i = 0; i < 7; i++) {
                        barValues[i].setText("0");
                        ViewGroup.LayoutParams params = bars[i].getLayoutParams();
                        params.height = 0;
                        bars[i].setLayoutParams(params);
                    }
                    return;
                }

                FitnessWeek week = response.body().get(0);

                // Gán dữ liệu tổng
                txtTotalSteps.setText(String.valueOf(week.tong_buoc));
                txtAverage.setText("Trung bình " + week.trung_binh);

                // Range tuần
                txtWeekRange.setText("Tháng " + week.thang + " "
                        + week.ngay_bat_dau.substring(8)
                        + "-" + week.ngay_ket_thuc.substring(8));

                // Thông tin nổi bật
                infoSteps.setText(week.noi_bat.so_buoc + "\nbước");
                infoCalories.setText(week.noi_bat.calo + "\nkcal");
                infoDistance.setText(week.noi_bat.quang_duong_m + "\nm");
                infoTime.setText(week.noi_bat.thoi_gian_phut + "\nphút");

                drawBarChart(week.chi_tiet);
            }

            @Override
            public void onFailure(Call<List<FitnessWeek>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Helper để đổi nhãn giữa
    private String getWeekLabel(String weekId) {
        LocalDate now = LocalDate.now();
        WeekFields wf = WeekFields.of(Locale.getDefault());
        int currentWeek = now.get(wf.weekOfWeekBasedYear());

        String[] arr = weekId.split("-W");
        int week = Integer.parseInt(arr[1]);

        int diff = currentWeek - week;

        if (diff == 0) return "Hôm nay";
        else if (diff > 0) return diff + (diff == 1 ? " tuần trước" : " tuần trước");
        else return Math.abs(diff) + (Math.abs(diff) == 1 ? " tuần sau" : " tuần sau");
    }


    private void drawBarChart(List<FitnessWeek.ChiTiet> list) {
        int max = getMaxSteps(list);

        for (int i = 0; i < 7; i++) {
            int steps = list.get(i).so_buoc;

            int height = (int) (180f * steps / max);

            ViewGroup.LayoutParams params = bars[i].getLayoutParams();
            params.height = height;
            bars[i].setLayoutParams(params);

            barValues[i].setText(String.valueOf(steps));
        }
    }

    private int getMaxSteps(List<FitnessWeek.ChiTiet> list) {
        int max = 1;
        for (FitnessWeek.ChiTiet c : list) {
            if (c.so_buoc > max) max = c.so_buoc;
        }
        return max;
    }
}
