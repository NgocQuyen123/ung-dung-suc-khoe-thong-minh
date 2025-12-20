package admin.example.ungdungsuckhoethongminh.fragments.weight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.weight.WeightHostActivity;

public class WeightOverviewFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hợp nhất tất cả các Ánh xạ View
        ConstraintLayout clWeightInput = view.findViewById(R.id.clWeightInput);
        ImageView ivBack = view.findViewById(R.id.ivBack);
        TextView tvAverageWeight = view.findViewById(R.id.tvAverageWeight);
        TextView tvCurrentWeight = view.findViewById(R.id.tvCurrentWeight);
        TextView tvBmiValue = view.findViewById(R.id.tvBmiValue);

        // 1. Thiết lập dữ liệu cứng (dữ liệu mẫu)
        tvAverageWeight.setText("65 kg");
        tvCurrentWeight.setText("60 kg");
        tvBmiValue.setText("22.5");

        // 2. Xử lý sự kiện click cho nút nhập cân nặng
        clWeightInput.setOnClickListener(v -> {
            if (getActivity() instanceof WeightHostActivity) {
                ((WeightHostActivity) getActivity()).navigateTo(new WeightAddFragment(), true);
            }
        });

        // 3. Xử lý sự kiện click cho nút Quay lại (Chuyển giao cho Activity)
        ivBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
    }
}