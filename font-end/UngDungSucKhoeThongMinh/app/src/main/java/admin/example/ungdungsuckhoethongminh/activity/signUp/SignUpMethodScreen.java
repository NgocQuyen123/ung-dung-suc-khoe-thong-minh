package admin.example.ungdungsuckhoethongminh.activity.signUp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import admin.example.ungdungsuckhoethongminh.R;

public class SignUpMethodScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_method_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Lấy nút Phone
        LinearLayout btnPhone = findViewById(R.id.roundedButtonPhone);
        btnPhone.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpMethodScreen.this, SignUpNumberActivity.class);
            startActivity(intent);

            // Optional: thêm animation
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_in);
        });
    }
}