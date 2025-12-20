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

        getSupportFragmentManager().addOnBackStackChangedListener(this::updateBackButtonVisibility);

        // Thay thế onBackPressed() bằng OnBackPressedCallback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onActivityBackClicked();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        if (savedInstanceState == null) {
            navigateTo(new WeightOverviewFragment(), false);
        }

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