package admin.example.ungdungsuckhoethongminh.activity.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import admin.example.ungdungsuckhoethongminh.R;

public class InfoEditGenderActivity extends AppCompatActivity {

    private ConstraintLayout flagContainer1, flagContainer2;
    private TextView txtMale, txtFemale;
    private Button btnSave;
    private ImageView btnClose;

    private String selectedGender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_edit_gender);

        // Ánh xạ view
        flagContainer1 = findViewById(R.id.flagContainer1);
        flagContainer2 = findViewById(R.id.flagContainer2);
        txtMale = findViewById(R.id.textView4);
        txtFemale = findViewById(R.id.textView5);
        btnSave = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.imgClose);

        // Nhận dữ liệu ban đầu
        selectedGender = getIntent().getStringExtra("gioiTinh");
        highlightSelected(); // Tô màu giới tính ban đầu

        // Nút Close
        btnClose.setOnClickListener(v -> finish());

        // Chọn Nam
        flagContainer1.setOnClickListener(v -> {
            selectedGender = "Nam";
            highlightSelected();
        });

        // Chọn Nữ
        flagContainer2.setOnClickListener(v -> {
            selectedGender = "Nữ";
            highlightSelected();
        });

        // Nút Lưu (không gọi API nữa)
        btnSave.setOnClickListener(v -> {
            if (selectedGender == null) {
                Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                return;
            }

            // Trả giới tính về Activity trước đó
            Intent data = new Intent();
            data.putExtra("gioiTinh", selectedGender);
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }

    private void highlightSelected() {
        // Reset
        flagContainer1.setBackgroundResource(R.drawable.input_bg_nocl);
        flagContainer2.setBackgroundResource(R.drawable.input_bg_nocl);

        // Tô màu item được chọn
        if ("Nam".equals(selectedGender)) {
            flagContainer1.setBackgroundResource(R.drawable.input_bg);
        } else if ("Nữ".equals(selectedGender)) {
            flagContainer2.setBackgroundResource(R.drawable.input_bg);
        }
    }
}
