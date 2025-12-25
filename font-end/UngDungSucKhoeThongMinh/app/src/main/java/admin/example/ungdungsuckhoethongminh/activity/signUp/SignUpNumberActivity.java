package admin.example.ungdungsuckhoethongminh.activity.signUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.auth.AuthActivity;

public class SignUpNumberActivity extends AppCompatActivity {
    Button btnNext;
    EditText edtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_number);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnNext = findViewById(R.id.btnNext);
        edtPhone = findViewById(R.id.edtPhone);
        btnNext.setOnClickListener(v -> {
            String number = edtPhone.getText().toString().trim();
            if (!number.isEmpty()) {
                // Lưu vào SharedPreferences
                SharedPreferences prefs = getSharedPreferences("MyAppData", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("phone", number);
                editor.apply();

                // Chuyển sang màn tiếp theo
                Intent intent = new Intent(SignUpNumberActivity.this, SignUpPhoneActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
            } else {
                edtPhone.setError("Vui lòng nhập số");
                edtPhone.requestFocus();
            }
        });

    }

}