package admin.example.ungdungsuckhoethongminh;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import admin.example.ungdungsuckhoethongminh.fragments.ActivityLevelFragment;
import admin.example.ungdungsuckhoethongminh.fragments.SummaryFragment;
import admin.example.ungdungsuckhoethongminh.fragments.WeightChangeRateFragment;
import admin.example.ungdungsuckhoethongminh.viewmodel.SharedViewModel;
import admin.example.ungdungsuckhoethongminh.fragments.TargetWeightFragment;

public class GoalWeightSettingActivity extends AppCompatActivity {

    private SharedViewModel vm;
    private ImageButton btnBack, btnClose;
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_weight_setting);
        btnBack = findViewById(R.id.btn_back);
        btnClose = findViewById(R.id.btn_close);
        TextView tvProgressText = findViewById(R.id.tv_progress_text);
        this.tvProgress = tvProgressText;
        vm = new ViewModelProvider(this).get(SharedViewModel.class);
        if (savedInstanceState == null) {
            vm.loadDataFromAssets();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new TargetWeightFragment())
                    .commit();

            updateProgressIndicator(new TargetWeightFragment());
        }
        btnBack.setOnClickListener(v -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().addOnBackStackChangedListener(new androidx.fragment.app.FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        androidx.fragment.app.Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.container);
                        if (currentFrag != null) {
                            updateProgressIndicator(currentFrag);
                        }
                        getSupportFragmentManager().removeOnBackStackChangedListener(this);
                    }
                });
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        });
        btnClose.setOnClickListener(v -> {
            finish();
        });
    }

    // helper to navigate to fragment by instance
    public void navigateTo(androidx.fragment.app.Fragment frag, boolean addToBackstack) {
        androidx.fragment.app.FragmentTransaction t = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, frag);
        if (addToBackstack) t.addToBackStack(null);
        t.commit();
        updateProgressIndicator(frag);
    }
    private void updateProgressIndicator(androidx.fragment.app.Fragment currentFrag) {

        final int TOTAL_STEPS = 4;

        int currentStep = 0;
        if (currentFrag instanceof TargetWeightFragment) {
            currentStep = 1;
        } else if (currentFrag instanceof ActivityLevelFragment) {
            currentStep = 2;
        } else if (currentFrag instanceof WeightChangeRateFragment) {
            currentStep = 3;
        }
        else if (currentFrag instanceof SummaryFragment) {
            currentStep = TOTAL_STEPS;
        }
        if (currentStep > 0) {
            tvProgress.setText(currentStep + "/" + TOTAL_STEPS);
        }
    }
    public SharedViewModel getViewModel() { return vm; }
}