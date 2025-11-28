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
import admin.example.ungdungsuckhoethongminh.model.SpeedWeightModel;

/**
 * Fragment để chọn tốc độ tăng/giảm cân.
 */
public class WeightPaceFragment extends Fragment {

    private final double CURRENT_WEIGHT = 65.0;
    private final double TARGET_WEIGHT = 60.0;
    private final String SELECTED_PACE_ID = "2";
    private final boolean IS_LOSING_WEIGHT = TARGET_WEIGHT < CURRENT_WEIGHT;

    private TextView tvFinishLabel;
    private TextView tvFinishDate;
    private RecyclerView rv;
    private PacePagerAdapter adapter;
    private Button btnNext;

    // Định nghĩa dữ liệu mẫu cho danh sách tốc độ
    private List<SpeedWeightModel> getPaces() {
        return Arrays.asList(
                new SpeedWeightModel("1", "Dễ", 0.25, 275),
                new SpeedWeightModel("2", "Vừa", 0.50, 550),
                new SpeedWeightModel("3", "Khó", 0.75, 825),
                new SpeedWeightModel("4", "Tối đa", 1.00, 1100)
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weight_pace, container, false);
        rv = root.findViewById(R.id.recyclerPace);
        btnNext = root.findViewById(R.id.btnNext);
        tvFinishLabel = root.findViewById(R.id.tvTitle);
        tvFinishDate = root.findViewById(R.id.tvFinishDate);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        List<SpeedWeightModel> paces = getPaces();

        adapter = new PacePagerAdapter(paces, item -> {
            updateGoalDate(paces, CURRENT_WEIGHT, TARGET_WEIGHT, item.id);
        }, IS_LOSING_WEIGHT);
        rv.setAdapter(adapter);

        adapter.setSelectedId(SELECTED_PACE_ID);
        updateGoalDate(paces, CURRENT_WEIGHT, TARGET_WEIGHT, SELECTED_PACE_ID);

        btnNext.setOnClickListener(v -> {
//            if (adapter != null && adapter.getSelectedId() != null) {
//                if (getActivity() instanceof WeightHeaderActivity) {
//                    ((WeightHeaderActivity) getActivity()).navigateTo(new WeighTargetCaloFragment(), true);
//                }
//            } else {
//                Toast.makeText(getContext(), "Vui lòng chọn tốc độ cân nặng.", Toast.LENGTH_SHORT).show();
//            }
            if (getActivity() instanceof WeightHeaderActivity) {
                ((WeightHeaderActivity) getActivity()).navigateTo(new WeighTargetCaloFragment(), true);
            }
        });

        return root;
    }

    private void updateGoalDate(List<SpeedWeightModel> paces, double currentWeight, double targetWeight, String selectedPaceId) {

        Optional<SpeedWeightModel> selectedPaceOpt = paces.stream()
                .filter(p -> p.id.equals(selectedPaceId))
                .findFirst();

        if (!selectedPaceOpt.isPresent()) {
            tvFinishDate.setText("---");
            return;
        }

        SpeedWeightModel selectedPace = selectedPaceOpt.get();

        double totalWeightDiff = Math.abs(targetWeight - currentWeight);
        double paceRate = Math.abs(selectedPace.tocDoKgTuan);

        if (paceRate <= 0 || totalWeightDiff == 0) {
            tvFinishDate.setText("Đã đạt mục tiêu");
            return;
        }

        double totalWeeks = totalWeightDiff / paceRate;
        long daysToAdd = Math.round(totalWeeks * 7);

        LocalDate goalDate = LocalDate.now().plusDays(daysToAdd);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'tháng' M, yyyy");
        String formattedDate = goalDate.format(formatter);

        tvFinishDate.setText(formattedDate);
    }
}