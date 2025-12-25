package admin.example.ungdungsuckhoethongminh.activity.weight;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.fragments.weight.WeightOverviewFragment;

public class WeightHostActivity extends AppCompatActivity {

    private static final int CONTAINER_ID = R.id.main_fragment_container;
    private ImageView btnBackHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_host);

        btnBackHost = findViewById(R.id.btn_back_host);
        btnBackHost.setOnClickListener(v -> onActivityBackClicked());

        // Đăng ký callback cho nút back hệ thống
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() { onActivityBackClicked(); }
        });

        if (savedInstanceState == null) {
            // CHỈ THỰC HIỆN TRONG NÀY
            // 1. Lấy dữ liệu từ Intent gửi từ WeighTargetCaloFragment
            double targetWeight = getIntent().getDoubleExtra("TARGET_WEIGHT", 0.0);

            // 2. Đóng gói vào Bundle
            Bundle bundle = new Bundle();
            bundle.putDouble("TARGET_WEIGHT", targetWeight);

            // 3. Khởi tạo và gắn dữ liệu cho Fragment
            WeightOverviewFragment overviewFragment = new WeightOverviewFragment();
            overviewFragment.setArguments(bundle);

            // 4. Chuyển màn hình
            navigateTo(overviewFragment, false);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this::updateBackButtonVisibility);
        updateBackButtonVisibility();
    }

    private void onActivityBackClicked() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    private void updateBackButtonVisibility() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            btnBackHost.setVisibility(View.VISIBLE);
        } else {
            btnBackHost.setVisibility(View.GONE);
        }
    }

    public void navigateTo(Fragment frag, boolean addToBackstack) {
        FragmentTransaction t = getSupportFragmentManager()
                .beginTransaction()
                .replace(CONTAINER_ID, frag);
        if (addToBackstack) {
            t.addToBackStack(null);
        }
        t.commit();

        getSupportFragmentManager().executePendingTransactions();
        updateBackButtonVisibility();
    }
}