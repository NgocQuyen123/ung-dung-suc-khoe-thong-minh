package admin.example.ungdungsuckhoethongminh;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneActivity extends AppCompatActivity {

    EditText edtPhone;
    Button btnSave;
    ImageView imgClose;

    String currentPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);

        edtPhone = findViewById(R.id.edtPhone);
        btnSave = findViewById(R.id.btnSave);
        imgClose = findViewById(R.id.imgClose);

        // nhận số điện thoại từ ProfileActivity
        currentPhone = getIntent().getStringExtra("sdt");
        if (currentPhone != null) {
            edtPhone.setText(currentPhone);
        }

        setupKeyboard();
        savePhoneNumber();
        setupCloseButton();
    }

    // =========================
    // SETUP BÀN PHÍM SỐ CUSTOM
    // =========================
    private void setupKeyboard() {
        int[] keyIds = {
                R.id.key1, R.id.key2, R.id.key3,
                R.id.key4, R.id.key5, R.id.key6,
                R.id.key7, R.id.key8, R.id.key9,
                R.id.key0
        };

        for (int id : keyIds) {
            LinearLayout key = findViewById(id);
            if (key != null) {
                key.setOnClickListener(v -> {
                    TextView txt = (TextView) key.getChildAt(0);
                    edtPhone.append(txt.getText().toString());
                });
            }
        }

        // nút xóa
        LinearLayout deleteKey = findViewById(R.id.keyDelete);
        if (deleteKey != null) {
            deleteKey.setOnClickListener(v -> {
                String current = edtPhone.getText().toString();
                if (!current.isEmpty()) {
                    edtPhone.setText(current.substring(0, current.length() - 1));
                }
            });
        }
    }

    // =========================
    // KHI BẤM NÚT LƯU
    // =========================
    private void savePhoneNumber() {
        btnSave.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString().trim();

            if (phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                return;
            }

            // KHÔNG GỌI API – Trả dữ liệu về
            Intent data = new Intent();
            data.putExtra("sdt", phone);
            setResult(RESULT_OK, data);

            finish();
        });
    }

    // =========================
    // NÚT CLOSE
    // =========================
    private void setupCloseButton() {
        imgClose.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
