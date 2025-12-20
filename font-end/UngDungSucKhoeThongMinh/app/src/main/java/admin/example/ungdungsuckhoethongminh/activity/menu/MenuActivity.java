package admin.example.ungdungsuckhoethongminh.activity.menu;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.MenuPagerAdapter;

public class MenuActivity extends AppCompatActivity {

    private ViewPager2 pager;

    private View navHomeContainer, navGiftContainer, navHeartContainer, navPillContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViews();
        setupPager();
        setupBottomNavClicks();
    }

    private void initViews() {
        pager = findViewById(R.id.swipePager);

        navHomeContainer = findViewById(R.id.navHomeContainer);
        navGiftContainer = findViewById(R.id.navGiftContainer);
        navHeartContainer = findViewById(R.id.navHeartContainer);
        navPillContainer = findViewById(R.id.navPillContainer);
    }

    private void setupPager() {
        pager.setAdapter(new MenuPagerAdapter(this));
    }

    private void setupBottomNavClicks() {
        navHomeContainer.setOnClickListener(v -> pager.setCurrentItem(0, true));
        navGiftContainer.setOnClickListener(v -> pager.setCurrentItem(1, true));
        navHeartContainer.setOnClickListener(v -> pager.setCurrentItem(2, true));
        navPillContainer.setOnClickListener(v -> pager.setCurrentItem(3, true));
    }
}
