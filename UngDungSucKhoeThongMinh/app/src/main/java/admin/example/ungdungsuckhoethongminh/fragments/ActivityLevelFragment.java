package admin.example.ungdungsuckhoethongminh.fragments;

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

import admin.example.ungdungsuckhoethongminh.GoalWeightSettingActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.ActivityAdapter;
import admin.example.ungdungsuckhoethongminh.model.MucDoHoatDong;

public class ActivityLevelFragment extends Fragment {

    private final int MOCK_ACTIVITY_ID = 2;

    private final int SELECTED_ACTIVITY_ID = 2;
    private RecyclerView rv;
    private ActivityAdapter adapter;
    private Button btnNext;

    private List<MucDoHoatDong> getActivities() {
        return Arrays.asList(
                new MucDoHoatDong(1, "Rất năng động", "Tập luyện trên 7 giờ mỗi tuần", 785),
                new MucDoHoatDong(2, "Hoạt động trung bình", "Tập luyện 4 - 6 giờ mỗi tuần", 471),
                new MucDoHoatDong(3, "Ít hoạt động", "Tập luyện 2 - 3 giờ mỗi tuần", 235),
                new MucDoHoatDong(4, "Thụ động", "Ít hoặc không tập thể dục", 118)
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_activity_level, container, false);

        rv = root.findViewById(R.id.rvActivity);
        btnNext = root.findViewById(R.id.btnNext);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        List<MucDoHoatDong> activities = getActivities();

        adapter = new ActivityAdapter(activities, selected -> {
        });
        rv.setAdapter(adapter);

        adapter.setSelectedId(SELECTED_ACTIVITY_ID);

        btnNext.setOnClickListener(v -> {
            if (adapter != null && adapter.getSelectedId() != -1) {
                if (getActivity() instanceof GoalWeightSettingActivity) {
                    ((GoalWeightSettingActivity)getActivity()).navigateTo(new WeightChangeRateFragment(), true);
                }
            } else {
                Toast.makeText(getContext(), "Vui lòng chọn mức độ hoạt động.", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}