package admin.example.ungdungsuckhoethongminh.activity.singIn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;

public class SignInNumber extends AppCompatActivity {

    private TextView tvTitle, tvSubtitle;
    private EditText edtPhone;
    private Button btnNext;
    private ImageView btnBack;

    // Các phím số
    private LinearLayout btnKey0, btnKey1, btnKey2, btnKey3, btnKey4,
            btnKey5, btnKey6, btnKey7, btnKey8, btnKey9;

    private FrameLayout btnDelete; // ❗ KHÔNG ĐƯỢC DÙNG LinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_number);

        initViews();
        setupKeyboard();
        setupNextButtonLogic();
        setupUIType();
        focusAndShowKeyboard();

        btnBack.setOnClickListener(v -> finish());

    }

    // ============================
    // 📌 1. ÁNH XẠ VIEW
    // ============================
    private void initViews() {

        tvTitle = findViewById(R.id.tvTitle);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        edtPhone = findViewById(R.id.edtPhone);

        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        btnKey0 = findViewById(R.id.btnKey0);
        btnKey1 = findViewById(R.id.btnKey1);
        btnKey2 = findViewById(R.id.btnKey2);
        btnKey3 = findViewById(R.id.btnKey3);
        btnKey4 = findViewById(R.id.btnKey4);
        btnKey5 = findViewById(R.id.btnKey5);
        btnKey6 = findViewById(R.id.btnKey6);
        btnKey7 = findViewById(R.id.btnKey7);
        btnKey8 = findViewById(R.id.btnKey8);
        btnKey9 = findViewById(R.id.btnKey9);
        btnDelete = findViewById(R.id.btnDelete);

        edtPhone.setFocusable(false);
    }

    // ============================
    // 📌 2. GIAO DIỆN: Login or Register
    // ============================
    private void setupUIType() {
        int type = 1; // 1 = đăng nhập, 2 = đăng ký

        if (type == 1) {
            tvTitle.setText("Đăng nhập bằng số điện thoại");
            tvSubtitle.setText("Nhập số điện thoại để đăng nhập");
        } else {
            tvTitle.setText("Tạo tài khoản bằng số điện thoại");
            tvSubtitle.setText("Nhập số điện thoại để tạo tài khoản");
        }
    }

    // ============================
    // 📌 3. LOGIC BÀN PHÍM SỐ
    // ============================
    private void setupKeyboard() {

        setKey(btnKey0, "0");
        setKey(btnKey1, "1");
        setKey(btnKey2, "2");
        setKey(btnKey3, "3");
        setKey(btnKey4, "4");
        setKey(btnKey5, "5");
        setKey(btnKey6, "6");
        setKey(btnKey7, "7");
        setKey(btnKey8, "8");
        setKey(btnKey9, "9");

        btnDelete.setOnClickListener(v -> {
            String t = edtPhone.getText().toString();
            if (!t.isEmpty()) {
                edtPhone.setText(t.substring(0, t.length() - 1));
            }
        });
    }

    // Hàm điền số
    private void setKey(LinearLayout keyLayout, String value) {
        keyLayout.setOnClickListener(v -> {
            edtPhone.setText(edtPhone.getText().toString() + value);
        });
    }

    // ============================
    // 📌 4. KÍCH HOẠT NÚT "TIẾP THEO"
    // ============================
    private void setupNextButtonLogic() {

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnNext.setEnabled(s.length() >= 9); // Đủ 9 số mới bật
            }
        });

        // Nhấn Tiếp theo
        btnNext.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString().trim();

            if (phone.length() < 9) {
                Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(SignInNumber.this, SignInPhoneActivity.class);
            intent.putExtra("phoneNumber", phone);
            startActivity(intent);

            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
        });
    }

    // ============================
    // 📌 5. TỰ ĐỘNG SHOW BÀN PHÍM
    // ============================
    private void focusAndShowKeyboard() {

        edtPhone.requestFocus();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.showSoftInput(edtPhone, InputMethodManager.SHOW_IMPLICIT);
            }

        }, 250);
    }
}