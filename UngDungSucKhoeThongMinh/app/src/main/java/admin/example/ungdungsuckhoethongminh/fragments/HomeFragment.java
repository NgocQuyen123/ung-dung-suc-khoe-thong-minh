package admin.example.ungdungsuckhoethongminh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import admin.example.ungdungsuckhoethongminh.ProfileActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.HealthAdapter;
import admin.example.ungdungsuckhoethongminh.model.HealthItem;

public class HomeFragment extends Fragment {

    private RecyclerView rvHealthItems;
    private HealthAdapter adapter;
    private List<HealthItem> healthItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvHealthItems = view.findViewById(R.id.rvHealthItems);
        rvHealthItems.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dữ liệu mẫu
        healthItems = new ArrayList<>();
        healthItems.add(new HealthItem("Đếm bước chân", "Cấp quyền bước chân", R.drawable.chaybo));
        healthItems.add(new HealthItem("Dinh dưỡng", "Thiết lập chương trình", R.drawable.ic_dinhduong));
        healthItems.add(new HealthItem("Mỡ trong máu", "Nhập ngay", R.drawable.ic_motrongmau));

        adapter = new HealthAdapter(healthItems);
        rvHealthItems.setAdapter(adapter);

        return view;
    }
}
