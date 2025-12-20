package admin.example.ungdungsuckhoethongminh.fragments.weight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import admin.example.ungdungsuckhoethongminh.activity.weight.WeightHeaderActivity;
import admin.example.ungdungsuckhoethongminh.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class WeightCreateFragment extends Fragment {

    private final double CURRENT_WEIGHT = 65.0;
    private final double TARGET_WEIGHT = 60.0;

    private EditText edtCurrent, edtTarget;
    private Button btnNext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weight_create, container, false);
        edtCurrent = root.findViewById(R.id.edtCurrentWeight);
        edtTarget = root.findViewById(R.id.edtTargetWeight);
        btnNext = root.findViewById(R.id.btnNext);

        edtCurrent.setText(String.valueOf(CURRENT_WEIGHT));
        edtTarget.setText(String.valueOf(TARGET_WEIGHT));

        btnNext.setOnClickListener(v -> {
            if (getActivity() instanceof WeightHeaderActivity) {
                ((WeightHeaderActivity) getActivity()).navigateTo(new WeightActivityLeverFragment(), true);
            }
        });

        return root;
    }
}