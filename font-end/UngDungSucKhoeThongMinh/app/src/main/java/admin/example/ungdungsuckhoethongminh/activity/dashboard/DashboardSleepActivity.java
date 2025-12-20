package admin.example.ungdungsuckhoethongminh.activity.dashboard;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.SleepPagerAdapter;

public class DashboardSleepActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sleep);

        // Ánh xạ các view
        btnBack = findViewById(R.id.btnBack);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Xử lý nút quay lại
        btnBack.setOnClickListener(v -> finish());

        // Thiết lập ViewPager và TabLayout
        viewPager.setAdapter(new SleepPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            String title = "";
            switch (position) {
                case 0: title = "Ngày"; break;
                case 1: title = "Tuần"; break;
                case 2: title = "Tháng"; break;
                case 3: title = "Năm"; break;
            }

            LinearLayout tabView = (LinearLayout) getLayoutInflater()
                    .inflate(R.layout.custom_tab, null, false);
            TextView tabText = tabView.findViewById(R.id.tabText);
            tabText.setText(title);

            if (position == 0) {
                tabView.setBackgroundResource(R.drawable.tab_background_selected);
                tabText.setTextColor(getResources().getColor(android.R.color.white));
            }

            tab.setCustomView(tabView);

        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout tabView = (LinearLayout) tab.getCustomView();
                if (tabView != null) {
                    tabView.setBackgroundResource(R.drawable.tab_background_selected);
                    TextView tabText = tabView.findViewById(R.id.tabText);
                    tabText.setTextColor(getResources().getColor(android.R.color.white));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout tabView = (LinearLayout) tab.getCustomView();
                if (tabView != null) {
                    tabView.setBackgroundResource(R.drawable.tab_background_unselected);
                    TextView tabText = tabView.findViewById(R.id.tabText);
                    tabText.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
}
