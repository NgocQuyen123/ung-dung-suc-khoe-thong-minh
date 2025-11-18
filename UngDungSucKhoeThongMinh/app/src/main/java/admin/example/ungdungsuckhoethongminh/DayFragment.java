
package admin.example.ungdungsuckhoethongminh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import admin.example.ungdungsuckhoethongminh.model.Fitness;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import admin.example.ungdungsuckhoethongminh.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayFragment extends Fragment {

    private ImageView imgCircle, imgCenterIcon;
    private TextView  txtSmallSteps, txtSmallCalories, txtSmallDistance, txtSmallTime;
    private TextView txtCenterTop, txtCenterBottom, txtDayInfo;
    private ImageButton btnPrevDay, btnNextDay;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Fitness currentFitness; // Lưu dữ liệu hiện tại

    public DayFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);

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

        // Hiển thị dữ liệu ngày hiện tại
        fetchFitnessData(sdf.format(calendar.getTime()));
        updateDayInfo();

        // Nút lùi ngày
        btnPrevDay.setOnClickListener(v -> {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            fetchFitnessData(sdf.format(calendar.getTime()));
            updateDayInfo();
        });

        // Nút tiến ngày
        btnNextDay.setOnClickListener(v -> {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            fetchFitnessData(sdf.format(calendar.getTime()));
            updateDayInfo();
        });

        // Sự kiện click các ô nhỏ để hiển thị giữa vòng tròn
        txtSmallSteps.setOnClickListener(v -> {
            if (currentFitness != null) {
                txtCenterTop.setText(String.valueOf(currentFitness.getSo_buoc()));
                txtCenterBottom.setText("bước");
                imgCenterIcon.setImageResource(R.drawable.vector); // icon bước chân
            }
        });

        txtSmallCalories.setOnClickListener(v -> {
            if (currentFitness != null) {
                txtCenterTop.setText(String.valueOf((int) currentFitness.getCalo()));
                txtCenterBottom.setText("kcal");
                imgCenterIcon.setImageResource(R.drawable.mdi_fire); // icon calo
            }
        });

        txtSmallDistance.setOnClickListener(v -> {
            if (currentFitness != null) {
                txtCenterTop.setText(String.valueOf((int) currentFitness.getQuang_duong_m()));
                txtCenterBottom.setText("m");
                imgCenterIcon.setImageResource(R.drawable.arrow_right); // icon quãng đường
            }
        });

        txtSmallTime.setOnClickListener(v -> {
            if (currentFitness != null) {
                int totalMinutes = 0;
                for (Fitness.Activity act : currentFitness.getHoat_dong()) {
                    totalMinutes += act.getThoi_gian_phut();
                }
                txtCenterTop.setText(String.valueOf(totalMinutes));
                txtCenterBottom.setText("phút");
                imgCenterIcon.setImageResource(R.drawable.mdi_light_clock); // icon thời gian
            }
        });


        return view;
    }

    private void fetchFitnessData(String date) {
        ApiService apiService = ApiClient.getApiService();
        Call<List<Fitness>> call = apiService.getFitnessByDate(date);

        call.enqueue(new Callback<List<Fitness>>() {
            @Override
            public void onResponse(Call<List<Fitness>> call, Response<List<Fitness>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    currentFitness = response.body().get(0);

                    txtSmallSteps.setText(currentFitness.getSo_buoc() + "\nbước");
                    txtSmallCalories.setText((int) currentFitness.getCalo() + "\nkcal");
                    txtSmallDistance.setText((int) currentFitness.getQuang_duong_m() + "\nm");

                    int totalMinutes = 0;
                    for (Fitness.Activity act : currentFitness.getHoat_dong()) {
                        totalMinutes += act.getThoi_gian_phut();
                    }
                    txtSmallTime.setText(totalMinutes + "\nphút");

                    // Mặc định hiển thị bước ở giữa vòng tròn
                    txtCenterTop.setText(String.valueOf(currentFitness.getSo_buoc()));
                    txtCenterBottom.setText("bước");

                } else {
                    resetData();
                    Toast.makeText(getContext(), "Không có dữ liệu cho ngày " + date, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Fitness>> call, Throwable t) {
                resetData();
                Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetData() {
        currentFitness = null;
        txtSmallSteps.setText("0\nbước");
        txtSmallCalories.setText("0\nkcal");
        txtSmallDistance.setText("0\nm");
        txtSmallTime.setText("0\nphút");
        txtCenterTop.setText("0");
        txtCenterBottom.setText("bước");
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

