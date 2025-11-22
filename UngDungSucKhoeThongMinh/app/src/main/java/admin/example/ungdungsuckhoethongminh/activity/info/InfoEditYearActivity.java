package admin.example.ungdungsuckhoethongminh.activity.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;

public class InfoEditYearActivity extends AppCompatActivity {

    private EditText edtBirthYear;
    private Button btnSave;
    private ImageView btnClose;

    private int userId;
    private int currentBirthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_edit_year);

        edtBirthYear = findViewById(R.id.edtBirthYear);
        btnSave = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.imgClose);

        // Nhận dữ liệu năm sinh từ Intent
        currentBirthYear = getIntent().getIntExtra("namSinh", 0);
        userId = getIntent().getIntExtra("id", 1);

        // Hiển thị năm sinh hiện tại
        if (currentBirthYear > 0) {
            edtBirthYear.setText(String.valueOf(currentBirthYear));
        }

        // Nút đóng
        btnClose.setOnClickListener(v -> finish());

        // Nút lưu (KHÔNG gọi API nữa)
        btnSave.setOnClickListener(v -> {
            String newYearStr = edtBirthYear.getText().toString().trim();

            if (newYearStr.isEmpty()) {
                Toast.makeText(InfoEditYearActivity.this, "Vui lòng nhập năm sinh", Toast.LENGTH_SHORT).show();
                return;
            }

            int newYear;
            try {
                newYear = Integer.parseInt(newYearStr);
            } catch (NumberFormatException e) {
                Toast.makeText(InfoEditYearActivity.this, "Năm sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Trả dữ liệu về Activity trước
            Intent data = new Intent();
            data.putExtra("namSinh", newYear);
            setResult(Activity.RESULT_OK, data);

            finish();
        });
    }
}
