package admin.example.ungdungsuckhoethongminh.activity.otp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import admin.example.ungdungsuckhoethongminh.R;

public class OTPMethodSelectionModal extends AppCompatActivity {

    private LinearLayout btnOtpZalo, btnOtpSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp_method_selection_modal);

        // Ánh xạ View
        btnOtpZalo = findViewById(R.id.btnOtpZalo);
        btnOtpSms  = findViewById(R.id.btnOtpSms);

        // Nhận số điện thoại truyền từ PhoneAuthScreen
        String phone = getIntent().getStringExtra("phoneNumber");

        // Khi bấm Zalo → mở màn OTP
        btnOtpZalo.setOnClickListener(v -> {
            Intent intent = new Intent(OTPMethodSelectionModal.this, OTPVerificationActivity.class);
            intent.putExtra("method", "zalo");
            intent.putExtra("phoneNumber", phone);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
        });

        // Khi bấm SMS → mở màn OTP
        btnOtpSms.setOnClickListener(v -> {
            Intent intent = new Intent(OTPMethodSelectionModal.this, OTPVerificationActivity.class);
            intent.putExtra("method", "sms");
            intent.putExtra("phoneNumber", phone);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
        });

        // Tự động xử lý inset
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}