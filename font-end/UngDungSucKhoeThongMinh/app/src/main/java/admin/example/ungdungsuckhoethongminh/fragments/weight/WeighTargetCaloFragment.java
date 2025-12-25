package admin.example.ungdungsuckhoethongminh.fragments.weight;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.weight.WeightHostActivity;

public class WeighTargetCaloFragment extends Fragment {

    private double targetWeight = 0.0;
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
    private static final int FRAGMENT_CONTAINER_ID = R.id.container;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // NHẬN dữ liệu từ WeightPaceFragment
        if (getArguments() != null) {
            targetWeight = getArguments().getDouble("TARGET_WEIGHT", 0.0);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weight_target_calo, container, false);

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
        if (itemBMRContainer != null) {
            itemBMRContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToBmrDetails();
                }
            });
        }
        tvTarget = root.findViewById(R.id.tvTarget);
        btnFinish = root.findViewById(R.id.btnFinish);

        updateSummary();

        btnFinish.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đã lưu mục tiêu calo và chuyển sang màn hình tổng quan.", Toast.LENGTH_SHORT).show();

            if (getActivity() != null) {
                // 1. Tạo Intent để chuyển sang WeightHostActivity
                Intent intent = new Intent(getActivity(), WeightHostActivity.class);
                intent.putExtra("TARGET_WEIGHT", targetWeight);
                // 2. Bắt đầu WeightHostActivity
                startActivity(intent);

                // 3. Kết thúc WeightHeaderActivity hiện tại
                getActivity().finish();
            }
        });

        return root;
    }


    private void updateSummary() {

        if (tvBmrValue != null) tvBmrValue.setText(BMR_CALORIES + " kcal");

        if (tvActivityValue != null) tvActivityValue.setText(ACTIVITY_CALORIES + " kcal");

        if (tvDeficitValue != null) tvDeficitValue.setText(SIGNED_DEFICIT + " kcal");

        if (tvTarget != null) tvTarget.setText(TOTAL_CALORIES + " kcal");
    }
    private void navigateToBmrDetails() {
        WeightBmrDetails bmrDetailsFragment = new WeightBmrDetails();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(FRAGMENT_CONTAINER_ID, bmrDetailsFragment);

        transaction.addToBackStack(null);

        transaction.commit();
    }
}