package admin.example.ungdungsuckhoethongminh.activity.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import admin.example.ungdungsuckhoethongminh.R;

public class CreateGenderActivity extends AppCompatActivity {

    private ConstraintLayout flagContainer1; // Nam
    private ConstraintLayout flagContainer2; // Nữ
    private Button btnSave;
    private String selectedGender = ""; // Lưu giới tính đã chọn

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_info_create_gender);

        // Ánh xạ
        flagContainer1 = findViewById(R.id.flagContainer1); // Nam
        flagContainer2 = findViewById(R.id.flagContainer2); // Nữ
        btnSave = findViewById(R.id.btnSave);

        // Sự kiện chọn Nam
        flagContainer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGender("Nam");
            }
        });

        // Sự kiện chọn Nữ
        flagContainer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGender("Nữ");
            }
        });

        // Nút Tiếp theo
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedGender.isEmpty()) {
                    Toast.makeText(CreateGenderActivity.this,
                            "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CreateGenderActivity.this, CreateHeightActivity.class);
                    intent.putExtra("gender", selectedGender);
                    startActivity(intent);
                }
            }
        });
    }

    // Hàm xử lý chọn giới tính
    private void selectGender(String gender) {
        selectedGender = gender;

        if (gender.equals("Nam")) {
            // Đổi màu nền Nam thành được chọn
            flagContainer1.setBackgroundResource(R.drawable.input_bg_selected);
            // Đổi màu nền Nữ về mặc định
            flagContainer2.setBackgroundResource(R.drawable.input_bg_nocl);
        } else {
            // Đổi màu nền Nữ thành được chọn
            flagContainer2.setBackgroundResource(R.drawable.input_bg_selected);
            // Đổi màu nền Nam về mặc định
            flagContainer1.setBackgroundResource(R.drawable.input_bg_nocl);
        }
    }
}