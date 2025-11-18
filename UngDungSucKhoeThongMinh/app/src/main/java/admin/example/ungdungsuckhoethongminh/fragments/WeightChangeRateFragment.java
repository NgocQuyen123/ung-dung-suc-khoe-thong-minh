package admin.example.ungdungsuckhoethongminh.fragments;

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
import java.util.List;
import java.util.Optional;

import admin.example.ungdungsuckhoethongminh.GoalWeightSettingActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.PaceAdapter;
import admin.example.ungdungsuckhoethongminh.model.TocDoCanNang;
import admin.example.ungdungsuckhoethongminh.viewmodel.SharedViewModel;

/**
 * Fragment để chọn tốc độ tăng/giảm cân.
 */
public class WeightChangeRateFragment extends Fragment {

    private TextView tvFinishLabel;
    private TextView tvFinishDate;
    private SharedViewModel vm;
    private RecyclerView rv;
    private PaceAdapter adapter;
    private Button btnNext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weight_change_rate, container, false);
        rv = root.findViewById(R.id.recyclerPace);
        btnNext = root.findViewById(R.id.btnNext);
        tvFinishLabel = root.findViewById(R.id.tvTitle);
        tvFinishDate = root.findViewById(R.id.tvFinishDate);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getActivity() instanceof GoalWeightSettingActivity) {
            vm = ((GoalWeightSettingActivity)getActivity()).getViewModel();
        }

        if (vm != null) {
            vm.appData.observe(getViewLifecycleOwner(), data -> {
                if (data != null && data.tocDoCanNang != null && vm.user.getValue() != null) {

                    double current = vm.user.getValue().canNangHienTai;
                    double target = vm.user.getValue().canNangMucTieu;

                    boolean isLosingWeight = target < current;
                    String selectedPaceId = vm.user.getValue().idTocDoCanNang;

                    // Khởi tạo adapter và Listener
                    adapter = new PaceAdapter(data.tocDoCanNang, item -> {
                        vm.updatePaceId(item.id);
                        // Cập nhật ngày khi chọn tốc độ mới
                        updateGoalDate(data.tocDoCanNang, current, target, item.id);
                    }, isLosingWeight);
                    rv.setAdapter(adapter);

                    // Đánh dấu item và cập nhật ngày khi LOAD lần đầu
                    if (selectedPaceId != null) {
                        adapter.setSelectedId(selectedPaceId);
                        updateGoalDate(data.tocDoCanNang, current, target, selectedPaceId);
                    } else {
                        tvFinishDate.setText("---");
                    }
                }
            });
        }

        btnNext.setOnClickListener(v -> {
            // Lưu ý: Cần thêm public String getSelectedId() vào PaceAdapter để dòng này hoạt động
            if (adapter != null && adapter.getSelectedId() != null) {
                if (getActivity() instanceof GoalWeightSettingActivity) {
                    ((GoalWeightSettingActivity) getActivity()).navigateTo(new SummaryFragment(), true);
                }
            } else {
                Toast.makeText(getContext(), "Vui lòng chọn tốc độ cân nặng.", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void updateGoalDate(List<TocDoCanNang> paces, double currentWeight, double targetWeight, String selectedPaceId) {

        Optional<TocDoCanNang> selectedPaceOpt = paces.stream()
                .filter(p -> p.id.equals(selectedPaceId))
                .findFirst();

        if (!selectedPaceOpt.isPresent()) {
            tvFinishDate.setText("---");
            return;
        }
        TocDoCanNang selectedPace = selectedPaceOpt.get();

        double totalWeightDiff = Math.abs(targetWeight - currentWeight);
        double paceRate = selectedPace.tocDoKgTuan;

        if (paceRate <= 0 || totalWeightDiff == 0) {
            // Nếu mục tiêu đã đạt hoặc tốc độ bằng 0
            tvFinishDate.setText("Đã đạt mục tiêu");
            return;
        }

        double totalWeeks = totalWeightDiff / paceRate;
        long daysToAdd = Math.round(totalWeeks * 7);

        LocalDate goalDate = LocalDate.now().plusDays(daysToAdd);

        // Định dạng tiếng Việt: "d 'tháng' M, yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'tháng' M, yyyy");
        String formattedDate = goalDate.format(formatter);

        tvFinishDate.setText(formattedDate);
    }
}