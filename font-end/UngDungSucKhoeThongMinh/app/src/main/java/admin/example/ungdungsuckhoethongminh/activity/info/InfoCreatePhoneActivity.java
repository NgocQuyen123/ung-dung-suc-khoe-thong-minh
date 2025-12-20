package admin.example.ungdungsuckhoethongminh.activity.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;

public class InfoCreatePhoneActivity extends AppCompatActivity {

    private EditText edtPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_info_create_phone);

        edtPhone = findViewById(R.id.edtPhone);

        // Bàn phím số
        setupKey(R.id.btnKey0, "0");
        setupKey(R.id.btnKey1, "1");
        setupKey(R.id.btnKey2, "2");
        setupKey(R.id.btnKey3, "3");
        setupKey(R.id.btnKey4, "4");
        setupKey(R.id.btnKey5, "5");
        setupKey(R.id.btnKey6, "6");
        setupKey(R.id.btnKey7, "7");
        setupKey(R.id.btnKey8, "8");
        setupKey(R.id.btnKey9, "9");

        // Nút xóa
        FrameLayout btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> {
            String current = edtPhone.getText().toString();
            if (current.length() > 0) {
                edtPhone.setText(current.substring(0, current.length() - 1));
            }
        });

        // Nút tiếp theo
        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(InfoCreatePhoneActivity.this, InfoCreateGenderActivity.class);
            startActivity(intent);
        });
    }

    /** Gắn sự kiện click cho mỗi phím số */
    private void setupKey(int layoutId, String value) {
        LinearLayout key = findViewById(layoutId);
        key.setOnClickListener(v -> {
            String current = edtPhone.getText().toString();
            edtPhone.setText(current + value);
        });
    }
}