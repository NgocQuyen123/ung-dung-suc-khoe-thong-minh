package admin.example.ungdungsuckhoethongminh.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import admin.example.ungdungsuckhoethongminh.GoalWeightSettingActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.ActivityAdapter;
import admin.example.ungdungsuckhoethongminh.viewmodel.SharedViewModel;

public class ActivityLevelFragment extends Fragment {

    private SharedViewModel vm;
    private RecyclerView rv;
    private ActivityAdapter adapter;
    private Button btnNext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_activity_level, container, false);

        rv = root.findViewById(R.id.rvActivity);
        btnNext = root.findViewById(R.id.btnNext);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getActivity() instanceof GoalWeightSettingActivity) {
            vm = ((GoalWeightSettingActivity)getActivity()).getViewModel();
        }

        if (vm != null) {
            vm.appData.observe(getViewLifecycleOwner(), data -> {
                if (data != null && data.mucDoHoatDong != null) {
                    adapter = new ActivityAdapter(data.mucDoHoatDong, selected -> {
                        vm.updateActivityId(selected.id);
                    });
                    rv.setAdapter(adapter);

                    if (vm.user.getValue() != null) {
                        adapter.setSelectedId(vm.user.getValue().idMucDoHoatDong);
                    }
                }
            });
        }

        btnNext.setOnClickListener(v -> {
            if (getActivity() instanceof GoalWeightSettingActivity) {
                ((GoalWeightSettingActivity)getActivity()).navigateTo(new WeightChangeRateFragment(), true);
            }
        });

        return root;
    }
}