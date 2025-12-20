package admin.example.ungdungsuckhoethongminh.activity.signUp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.otp.OTPVerificationActivity;

public class SignUpPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_phone);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Lấy view btnOtpSms
        findViewById(R.id.btnOtpSms).setOnClickListener(v -> {
            // Tạo intent để mở OtpVerificationActivity
            Intent intent = new Intent(SignUpPhoneActivity.this, OTPVerificationActivity.class);
            intent.putExtra("flow", "signup");
            startActivity(intent);
        });
    }

}