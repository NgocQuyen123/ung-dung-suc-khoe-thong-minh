package admin.example.ungdungsuckhoethongminh.fragments.weight;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import admin.example.ungdungsuckhoethongminh.activity.weight.WeightHeaderActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.PacePagerAdapter;
import admin.example.ungdungsuckhoethongminh.model.NhipDoCanNangModel;
import admin.example.ungdungsuckhoethongminh.model.SpeedWeightModel;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment để chọn tốc độ tăng/giảm cân.
 */
public class WeightPaceFragment extends Fragment {

    private TextView tvFinishLabel;
    private TextView tvFinishDate;
    private RecyclerView rv;
    private PacePagerAdapter adapter;
    private Button btnNext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weight_pace, container, false);

        rv = root.findViewById(R.id.recyclerPace);
        btnNext = root.findViewById(R.id.btnNext);
        tvFinishLabel = root.findViewById(R.id.tvTitle);
        tvFinishDate = root.findViewById(R.id.tvFinishDate);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lấy dữ liệu từ Bundle
        Bundle args = getArguments();
        double currentWeight = args != null ? args.getDouble("CURRENT_WEIGHT", 65.0) : 65.0;
        double targetWeight = args != null ? args.getDouble("TARGET_WEIGHT", 60.0) : 60.0;
        final boolean isLosingWeight = targetWeight < currentWeight;

        loadPacesFromApi(currentWeight, targetWeight, isLosingWeight);

        btnNext.setOnClickListener(v -> {
            if (getActivity() instanceof WeightHeaderActivity) {
                Bundle bundle = new Bundle();
                bundle.putDouble("CURRENT_WEIGHT", currentWeight); // Cần đảm bảo biến này đã lấy từ args ở trên
                bundle.putDouble("TARGET_WEIGHT", targetWeight);

                WeighTargetCaloFragment targetFragment = new WeighTargetCaloFragment();
                targetFragment.setArguments(bundle);

                ((WeightHeaderActivity) getActivity()).navigateTo(targetFragment, true);
            }
        });

        return root;
    }

    private void loadPacesFromApi(double currentWeight, double targetWeight, boolean isLosingWeight) {
        ApiClient.getCanNangApi().getAllNhipDoCanNang().enqueue(new Callback<List<NhipDoCanNangModel>>() {
            @Override
            public void onResponse(Call<List<NhipDoCanNangModel>> call, Response<List<NhipDoCanNangModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NhipDoCanNangModel> paces = response.body();

                    adapter = new PacePagerAdapter(paces, item -> {
                        updateGoalDate(paces, currentWeight, targetWeight, item.getId().toString());
                    }, isLosingWeight);

                    rv.setAdapter(adapter);
                    adapter.setSelectedId("2"); // default
                    updateGoalDate(paces, currentWeight, targetWeight, "2");
                } else {
                    tvFinishDate.setText("---");
                }
            }

            @Override
            public void onFailure(Call<List<NhipDoCanNangModel>> call, Throwable t) {
                tvFinishDate.setText("---");
            }
        });
    }

    private void updateGoalDate(List<NhipDoCanNangModel> paces, double currentWeight, double targetWeight, String selectedPaceId) {
        Optional<NhipDoCanNangModel> selectedPaceOpt = paces.stream()
                .filter(p -> p.getId().toString().equals(selectedPaceId))
                .findFirst();

        if (!selectedPaceOpt.isPresent()) {
            tvFinishDate.setText("---");
            return;
        }

        NhipDoCanNangModel selectedPace = selectedPaceOpt.get();
        double totalWeightDiff = Math.abs(targetWeight - currentWeight);
        double paceRate = Math.abs(selectedPace.getTocDoKgTuan());

        if (paceRate <= 0 || totalWeightDiff == 0) {
            tvFinishDate.setText("Đã đạt mục tiêu");
            return;
        }

        double totalWeeks = totalWeightDiff / paceRate;
        long daysToAdd = Math.round(totalWeeks * 7);

        LocalDate goalDate = LocalDate.now().plusDays(daysToAdd);
        tvFinishDate.setText(goalDate.format(DateTimeFormatter.ofPattern("d 'tháng' M, yyyy")));
    }

}