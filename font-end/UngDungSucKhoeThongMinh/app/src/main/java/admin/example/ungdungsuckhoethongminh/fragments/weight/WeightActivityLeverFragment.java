package admin.example.ungdungsuckhoethongminh.fragments.weight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;


import admin.example.ungdungsuckhoethongminh.activity.weight.WeightHeaderActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.ActivityPagerAdapter;
import admin.example.ungdungsuckhoethongminh.model.LeverModel;
import admin.example.ungdungsuckhoethongminh.model.MucDoHoatDongModel;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import admin.example.ungdungsuckhoethongminh.network.CanNangApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeightActivityLeverFragment extends Fragment {

    private final int MOCK_ACTIVITY_ID = 2;

    private int selectedActivityId = 2;
    private RecyclerView rv;
    private ActivityPagerAdapter adapter;
    private Button btnNext;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weight_activity_level, container, false);

        rv = root.findViewById(R.id.rvActivity);
        btnNext = root.findViewById(R.id.btnNext);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        loadActivities(); // load dữ liệu từ BE

        btnNext.setOnClickListener(v -> {
            if (adapter != null && selectedActivityId != 2) {
                if (getActivity() instanceof WeightHeaderActivity) {
                    ((WeightHeaderActivity)getActivity()).navigateTo(new WeightPaceFragment(), true);
                }
            } else {
                Toast.makeText(getContext(), "Vui lòng chọn mức độ hoạt động.", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
    private void loadActivities() {
        CanNangApi api = ApiClient.getCanNangApi();
        api.getAllMucDoHoatDong().enqueue(new Callback<List<MucDoHoatDongModel>>() {
            @Override
            public void onResponse(Call<List<MucDoHoatDongModel>> call, Response<List<MucDoHoatDongModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MucDoHoatDongModel> activities = response.body();
                    adapter = new ActivityPagerAdapter(activities, selected -> {
                        selectedActivityId = selected.getId(); // lưu ID được chọn
                    });
                    rv.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu mức độ hoạt động", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MucDoHoatDongModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Không thể tải dữ liệu mức độ hoạt động", Toast.LENGTH_SHORT).show();
            }
        });
    }
}