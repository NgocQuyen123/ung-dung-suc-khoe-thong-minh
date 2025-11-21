package admin.example.ungdungsuckhoethongminh.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import admin.example.ungdungsuckhoethongminh.GoalWeightSettingActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.model.ThongTinCanNang;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class TargetWeightFragment extends Fragment {

    private final double CURRENT_WEIGHT = 65.0;
    private final double TARGET_WEIGHT = 60.0;

    private EditText edtCurrent, edtTarget;
    private Button btnNext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_target_weight, container, false);
        edtCurrent = root.findViewById(R.id.edtCurrentWeight);
        edtTarget = root.findViewById(R.id.edtTargetWeight);
        btnNext = root.findViewById(R.id.btnNext);

        edtCurrent.setText(String.valueOf(CURRENT_WEIGHT));
        edtTarget.setText(String.valueOf(TARGET_WEIGHT));

        btnNext.setOnClickListener(v -> {
            if (getActivity() instanceof GoalWeightSettingActivity) {
                ((GoalWeightSettingActivity) getActivity()).navigateTo(new ActivityLevelFragment(), true);
            }
        });

        return root;
    }
}