package admin.example.ungdungsuckhoethongminh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterNumberActivity extends AppCompatActivity {
    Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_number);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Lấy reference đến Button
        btnNext = findViewById(R.id.btnNext);

        // Thiết lập sự kiện click
        btnNext.setOnClickListener(v -> {
            // Mở activity mới
            Intent intent = new Intent(RegisterNumberActivity.this, RegisterPhoneActivity.class);
            startActivity(intent);

            // Optional: thêm animation
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_in);
        });

    }

}