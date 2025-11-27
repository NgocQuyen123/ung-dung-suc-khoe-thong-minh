package admin.example.ungdungsuckhoethongminh.fragments.weight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import admin.example.ungdungsuckhoethongminh.R;

public class WeightBmrDetails extends Fragment {

    private ImageView ivBackIndicator;
    private Button btnSave;

    private TextView tvBmrResult;
    private TextView tvWeightValue;
    private TextView tvHeightValue;
    private TextView tvBirthYearValue;
    private TextView tvGenderValue;

    public WeightBmrDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_bmr_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Ánh xạ View
        // LƯU Ý: Đã loại bỏ ánh xạ ivBack để tránh lỗi NullPointerException
        // vì nó không có trong XML đã cung cấp.
        ivBackIndicator = view.findViewById(R.id.ivBackIndicator);
        btnSave = view.findViewById(R.id.btnSave);

        tvBmrResult = view.findViewById(R.id.tvBmrResult);
        tvWeightValue = view.findViewById(R.id.tvWeightValue);
        tvHeightValue = view.findViewById(R.id.tvHeightValue);
        tvBirthYearValue = view.findViewById(R.id.tvBirthYearValue);
        tvGenderValue = view.findViewById(R.id.tvGenderValue);

        // 2. Thiết lập dữ liệu (Dữ liệu cứng mẫu)
        tvBmrResult.setText("1,570");
        tvWeightValue.setText("60 kg");
        tvHeightValue.setText("165 cm");
        tvBirthYearValue.setText("2005");
        tvGenderValue.setText("Nam");


        // 3. Xử lý sự kiện click cho nút Quay lại
        View.OnClickListener backClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        };

        ivBackIndicator.setOnClickListener(backClickListener);

        // 4. Xử lý sự kiện click cho nút Lưu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Đã lưu thông tin BMR", Toast.LENGTH_SHORT).show();

                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });
    }
}