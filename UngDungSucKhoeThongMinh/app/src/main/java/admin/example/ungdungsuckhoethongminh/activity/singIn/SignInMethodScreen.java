package admin.example.ungdungsuckhoethongminh.activity.singIn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;

public class SignInMethodScreen extends AppCompatActivity {

    private Button btnPhone;
    private ImageView btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_method_screen);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnPhone    = findViewById(R.id.button4);
        btnClose    = findViewById(R.id.imageView);
    }

    private void setupEvents() {
        btnPhone.setOnClickListener(v -> {
            Intent intent = new Intent(SignInMethodScreen.this, SignInNumber.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
        });

        // Nút đóng → về MainActivity
        btnClose.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }
}