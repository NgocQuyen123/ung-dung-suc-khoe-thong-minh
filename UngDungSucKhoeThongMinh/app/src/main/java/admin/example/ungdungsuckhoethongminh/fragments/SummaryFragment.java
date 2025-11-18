package admin.example.ungdungsuckhoethongminh.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import admin.example.ungdungsuckhoethongminh.GoalWeightSettingActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.model.ThongTinCanNang;
import admin.example.ungdungsuckhoethongminh.viewmodel.SharedViewModel;

public class SummaryFragment extends Fragment {

    private SharedViewModel vm;

    // Khai báo các TextView chứa giá trị (Giá trị trong item_summary_detail.xml)
    private TextView tvBmrValue;
    private TextView tvActivityValue;
    private TextView tvDeficitValue;
    private TextView tvTarget;
    private Button btnFinish;

    // Khai báo các Container (để tìm TextView bên trong)
    private ConstraintLayout itemBMRContainer;
    private ConstraintLayout itemActivityContainer;
    private ConstraintLayout itemDeficitContainer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_summary, container, false);

        // 1. Ánh xạ các Container và TextView giá trị (Giá trị TV trong các item)
        itemBMRContainer = root.findViewById(R.id.itemBMR);
        itemActivityContainer = root.findViewById(R.id.itemActivity);
        itemDeficitContainer = root.findViewById(R.id.itemDeficit);

        if (itemBMRContainer != null) {
            tvBmrValue = itemBMRContainer.findViewById(R.id.tvBMRValue);
        } else {
            tvBmrValue = root.findViewById(R.id.tvBMRValue);
        }

        if (itemActivityContainer != null) {
            tvActivityValue = itemActivityContainer.findViewById(R.id.tvActivityValue);
        } else {
            tvActivityValue = root.findViewById(R.id.tvActivityValue);
        }

        if (itemDeficitContainer != null) {
            tvDeficitValue = itemDeficitContainer.findViewById(R.id.tvDeficitValue);
        } else {
            tvDeficitValue = root.findViewById(R.id.tvDeficitValue);
        }

        tvTarget = root.findViewById(R.id.tvTarget);
        btnFinish = root.findViewById(R.id.btnFinish);


        if (getActivity() instanceof GoalWeightSettingActivity) {
            vm = ((GoalWeightSettingActivity)getActivity()).getViewModel();
        }

        if (vm != null) {
            vm.user.observe(getViewLifecycleOwner(), u -> updateSummary());
            vm.appData.observe(getViewLifecycleOwner(), d -> updateSummary());
        }

        btnFinish.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
        });

        return root;
    }

    private void updateSummary() {
        ThongTinCanNang u = vm.user.getValue();
        if (u == null || vm.appData.getValue() == null) return;

        double bmr = vm.calcBMR(u);
        int act = vm.getActivityCalories(u);
        int signedDeficit = vm.getSignedDeficit();
        int total = vm.calcTargetCalories();

        // Gán giá trị BMR
        if (tvBmrValue != null) tvBmrValue.setText(Math.round(bmr) + " kcal");

        // Gán giá trị Calo Hoạt động
        if (tvActivityValue != null) tvActivityValue.setText(act + " kcal");

        // Gán giá trị Thâm hụt (Xử lý dấu +/-)
        String sign = signedDeficit > 0 ? "+" : "";
        if (tvDeficitValue != null) tvDeficitValue.setText(sign + signedDeficit + " kcal");

        // Gán giá trị Tổng calo mục tiêu
        if (tvTarget != null) tvTarget.setText(total + " kcal");
    }
}