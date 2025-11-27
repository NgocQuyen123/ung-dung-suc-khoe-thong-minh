package admin.example.ungdungsuckhoethongminh.activity.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.auth.AuthActivity;

public class CreateYearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_info_create_year);

        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            Intent intent = new Intent(CreateYearActivity.this, AuthActivity.class);
            startActivity(intent);
        });
    }
}
