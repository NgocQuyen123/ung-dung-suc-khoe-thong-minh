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

public class SummaryFragment extends Fragment {

    private final int BMR_CALORIES = 1586;
    private final int ACTIVITY_CALORIES = 471;
    private final int SIGNED_DEFICIT = -550;
    private final int TOTAL_CALORIES = 1507;

    private TextView tvBmrValue;
    private TextView tvActivityValue;
    private TextView tvDeficitValue;
    private TextView tvTarget;
    private Button btnFinish;

    private ConstraintLayout itemBMRContainer;
    private ConstraintLayout itemActivityContainer;
    private ConstraintLayout itemDeficitContainer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_summary, container, false);

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

        updateSummary();

        btnFinish.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Lưu thành công (Test)", Toast.LENGTH_SHORT).show();
        });

        return root;
    }

    private void updateSummary() {

        if (tvBmrValue != null) tvBmrValue.setText(BMR_CALORIES + " kcal");

        if (tvActivityValue != null) tvActivityValue.setText(ACTIVITY_CALORIES + " kcal");

        if (tvDeficitValue != null) tvDeficitValue.setText(SIGNED_DEFICIT + " kcal");

        if (tvTarget != null) tvTarget.setText(TOTAL_CALORIES + " kcal");
    }
}