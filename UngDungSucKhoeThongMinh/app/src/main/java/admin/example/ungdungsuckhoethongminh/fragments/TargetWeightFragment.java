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
import admin.example.ungdungsuckhoethongminh.viewmodel.SharedViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class TargetWeightFragment extends Fragment {

    private SharedViewModel vm;
    private EditText edtCurrent, edtTarget;
    private Button btnNext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_target_weight, container, false);
        edtCurrent = root.findViewById(R.id.edtCurrentWeight);
        edtTarget = root.findViewById(R.id.edtTargetWeight);
        btnNext = root.findViewById(R.id.btnNext);

        vm = ((GoalWeightSettingActivity) getActivity()).getViewModel();

        vm.user.observe(getViewLifecycleOwner(), new Observer<ThongTinCanNang>() {
            @Override
            public void onChanged(ThongTinCanNang t) {
                if (t != null) {
                    edtCurrent.setText(String.valueOf(t.canNangHienTai));
                    edtTarget.setText(String.valueOf(t.canNangMucTieu));
                }
            }
        });

        btnNext.setOnClickListener(v -> {
            String cur = edtCurrent.getText().toString();
            String tgt = edtTarget.getText().toString();
            if (!cur.isEmpty()) vm.updateWeight(Double.parseDouble(cur));
            if (!tgt.isEmpty()) vm.updateTargetWeight(Double.parseDouble(tgt));
            // go to next fragment
            ((GoalWeightSettingActivity) getActivity()).navigateTo(new ActivityLevelFragment(), true);
        });

        return root;
    }
}