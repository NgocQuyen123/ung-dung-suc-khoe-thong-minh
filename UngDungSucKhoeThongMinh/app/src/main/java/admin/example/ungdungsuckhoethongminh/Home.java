package admin.example.ungdungsuckhoethongminh;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.viewpager2.widget.ViewPager2;

public class Home extends AppCompatActivity {

    private TabLayout tabLayoutHome;
    private ViewPager2 viewPagerHome;

    private final String[] tabTitles = new String[]{"Trang chủ", "Quà tặng", "Sức khỏe", "Thuốc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayoutHome = findViewById(R.id.tabLayoutHome);
        viewPagerHome = findViewById(R.id.viewPagerHome);

        HomePagerAdapter adapter = new HomePagerAdapter(this);
        viewPagerHome.setAdapter(adapter);

        // Kết nối TabLayout và ViewPager2
        new TabLayoutMediator(tabLayoutHome, viewPagerHome,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
    }
}
