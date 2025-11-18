package admin.example.ungdungsuckhoethongminh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SignInMethodScreen extends AppCompatActivity {

    private Button btnGoogle, btnFacebook, btnEmail, btnPhone;
    private ImageView btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_method_screen);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnGoogle   = findViewById(R.id.button);   // nút đen — Google
        btnFacebook = findViewById(R.id.button2);  // Facebook
        btnEmail    = findViewById(R.id.button3);  // Email
        btnPhone    = findViewById(R.id.button4);  // Phone
        btnClose    = findViewById(R.id.imageView);
    }

    private void setupEvents() {

        // Google login — bạn xử lý sau
        btnGoogle.setOnClickListener(v -> {
            // TODO: login Google
        });

        // Facebook login — bạn xử lý sau
        btnFacebook.setOnClickListener(v -> {
            // TODO: login Facebook
        });

        // Đăng nhập bằng Email — mở màn hình email nếu bạn có
        btnEmail.setOnClickListener(v -> {
            // TODO: login email
        });

        // ⭐⭐ QUAN TRỌNG: ĐĂNG NHẬP BẰNG PHONE ⭐⭐
        btnPhone.setOnClickListener(v -> {
            Intent intent = new Intent(SignInMethodScreen.this, PhoneAuthScreen.class);
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
