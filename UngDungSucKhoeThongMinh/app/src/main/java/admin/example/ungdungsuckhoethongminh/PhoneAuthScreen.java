package admin.example.ungdungsuckhoethongminh;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PhoneAuthScreen extends AppCompatActivity {

    private TextView tvTitle, tvSubtitle;
    private EditText edtPhone;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth_screen);

        tvTitle = findViewById(R.id.tvTitle);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        edtPhone = findViewById(R.id.edtPhone);
        btnNext = findViewById(R.id.btnNext);

        // Tạm test: đổi giữa 1 và 2 để xem kết quả
        int type = 1; // 1 = đăng nhập, 2 = tạo tài khoản

        if (type == 1) {
            tvTitle.setText("Đăng nhập bằng số điện thoại");
            tvSubtitle.setText("Nhập số điện thoại để đăng nhập");
        } else if (type == 2) {
            tvTitle.setText("Tạo tài khoản bằng số điện thoại");
            tvSubtitle.setText("Nhập số điện thoại để tạo tài khoản");
        }
        edtPhone.requestFocus();

        // Mở bàn phím với delay nhỏ để UI load xong
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(edtPhone, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);
    }
}