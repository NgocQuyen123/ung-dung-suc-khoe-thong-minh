package admin.example.ungdungsuckhoethongminh.activity.weight;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.fragments.weight.WeighTargetCaloFragment;
import admin.example.ungdungsuckhoethongminh.fragments.weight.WeightActivityLeverFragment;
import admin.example.ungdungsuckhoethongminh.fragments.weight.WeightPaceFragment;
import admin.example.ungdungsuckhoethongminh.fragments.weight.WeightCreateFragment;

public class WeightHeaderActivity extends AppCompatActivity {

    private ImageButton btnBack, btnClose;
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_header);

        btnBack = findViewById(R.id.btn_back);
        btnClose = findViewById(R.id.btn_close);
        TextView tvProgressText = findViewById(R.id.tv_progress_text);
        this.tvProgress = tvProgressText;


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new WeightCreateFragment())
                    .commit();

            updateProgressIndicator(new WeightCreateFragment());
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
        if (currentFrag instanceof WeightCreateFragment) {
            currentStep = 1;
        } else if (currentFrag instanceof WeightActivityLeverFragment) {
            currentStep = 2;
        } else if (currentFrag instanceof WeightPaceFragment) {
            currentStep = 3;
        }
        else if (currentFrag instanceof WeighTargetCaloFragment) {
            currentStep = TOTAL_STEPS;
        }
        if (currentStep > 0) {
            tvProgress.setText(currentStep + "/" + TOTAL_STEPS);
        }
    }
}