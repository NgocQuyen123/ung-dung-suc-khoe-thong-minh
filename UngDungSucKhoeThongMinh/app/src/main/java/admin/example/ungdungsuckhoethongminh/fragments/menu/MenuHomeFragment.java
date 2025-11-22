package admin.example.ungdungsuckhoethongminh.fragments.menu;

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

import admin.example.ungdungsuckhoethongminh.activity.info.InfoProfileActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.dashboard.DashboardSleepActivity;
import admin.example.ungdungsuckhoethongminh.activity.dashboard.DashboardStepActivity;
import admin.example.ungdungsuckhoethongminh.adapters.HealthPagerAdapter;
import admin.example.ungdungsuckhoethongminh.model.HealthItemModel;

public class MenuHomeFragment extends Fragment {

    private RecyclerView rvHealthItems;
    private HealthPagerAdapter adapter;
    private List<HealthItemModel> healthItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_home, container, false);

        rvHealthItems = view.findViewById(R.id.rvHealthItems);
        rvHealthItems.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dữ liệu mẫu
        healthItems = new ArrayList<>();
        healthItems.add(new HealthItemModel("Đếm bước chân", "Cấp quyền bước chân", R.drawable.chaybo));
        healthItems.add(new HealthItemModel("Cân nặng", "Thiết lập chương trình", R.drawable.ic_dinhduong));
        healthItems.add(new HealthItemModel("Giấc ngủ", "Nhập ngay", R.drawable.ic_motrongmau));

        adapter = new HealthPagerAdapter(healthItems, position -> {
            HealthItemModel clickedItem = healthItems.get(position);

            switch (clickedItem.getTitle()) {
                case "Đếm bước chân":
                    // Mở StepsActivity
                    startActivity(new Intent(getActivity(), DashboardStepActivity.class));
                    break;
                case "Cân nặng":
                    // Mở WeightActivity (tạo layout/activity tương ứng)
//                    startActivity(new Intent(getActivity(), WeightActivity.class));
                    break;
                case "Giấc ngủ":
                    // Mở SleepActivity
                    startActivity(new Intent(getActivity(), DashboardSleepActivity.class));
                    break;
                default:
                    break;
            }
        });
        rvHealthItems.setAdapter(adapter);

        rvHealthItems.setAdapter(adapter);

        // Lấy FrameLayout từ view đã inflate
        FrameLayout frameProfile = view.findViewById(R.id.frameProfile);

        // Click mở ProfileActivity
        frameProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InfoProfileActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
