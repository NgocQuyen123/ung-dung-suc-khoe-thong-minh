package admin.example.ungdungsuckhoethongminh.fragments.weight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import admin.example.ungdungsuckhoethongminh.activity.weight.WeightHeaderActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.model.CanNangHienTaiResponse;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import admin.example.ungdungsuckhoethongminh.network.CanNangApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class WeightCreateFragment extends Fragment {

    private int userId = 1;
    private EditText edtCurrent, edtTarget;
    private Button btnNext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weight_create, container, false);
        edtCurrent = root.findViewById(R.id.edtCurrentWeight);
        edtTarget = root.findViewById(R.id.edtTargetWeight);
        btnNext = root.findViewById(R.id.btnNext);

        loadCurrentWeight(); // load cân nặng từ BE

        btnNext.setOnClickListener(v -> {
            double targetWeight = Double.parseDouble(edtTarget.getText().toString());
            double currentWeight = Double.parseDouble(edtCurrent.getText().toString());

            if (getActivity() instanceof WeightHeaderActivity) {
                WeightActivityLeverFragment fragment = new WeightActivityLeverFragment();
                Bundle bundle = new Bundle();
                bundle.putDouble("CURRENT_WEIGHT", currentWeight);
                bundle.putDouble("TARGET_WEIGHT", targetWeight);
                fragment.setArguments(bundle);

                ((WeightHeaderActivity) getActivity()).navigateTo(fragment, true);

            }
        });
        return root;
    }

    private void loadCurrentWeight() {
        CanNangApi api = ApiClient.getCanNangApi();
        api.getCanNangHienTai(userId).enqueue(new Callback<CanNangHienTaiResponse>() {
            @Override
            public void onResponse(Call<CanNangHienTaiResponse> call, Response<CanNangHienTaiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    double currentWeight = response.body().getCanNangHienTai();
                    edtCurrent.setText(String.valueOf(currentWeight));
                } else {
                    Toast.makeText(getContext(), "Không thể lấy cân nặng hiện tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CanNangHienTaiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi mạng khi lấy cân nặng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}