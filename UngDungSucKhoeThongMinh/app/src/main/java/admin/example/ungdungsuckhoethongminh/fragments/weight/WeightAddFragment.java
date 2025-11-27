package admin.example.ungdungsuckhoethongminh.fragments.weight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.weight.WeightHostActivity;
import admin.example.ungdungsuckhoethongminh.fragments.weight.WeightCreateFragment;

public class WeightAddFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ View
        Button btnSave = view.findViewById(R.id.btnSave);
        ImageView ivBack = view.findViewById(R.id.ivBack);

        // ⭐ Khắc phục lỗi: Kiểm tra null trước khi thiết lập Listener
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Đã lưu cân nặng", Toast.LENGTH_SHORT).show();

                // ⭐ Logic Quay lại đơn giản (popBackStack)
                if (getActivity() != null) {
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        // ⭐ Khắc phục lỗi: Kiểm tra null cho nút Back
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            });
        }
    }
}