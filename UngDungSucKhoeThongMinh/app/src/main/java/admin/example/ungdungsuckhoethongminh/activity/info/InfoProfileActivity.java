package admin.example.ungdungsuckhoethongminh.activity.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;

public class InfoProfileActivity extends AppCompatActivity {

    private TextView txtName, txtPhone, txtGender, txtHeight, txtBirth;
    private LinearLayout btnEditName, btnEditPhone, btnEditGender, btnEditHeight, btnEditBirth;
    private ImageView btnBack;
    private static final int REQUEST_EDIT_NAME = 1;
    private static final int REQUEST_EDIT_PHONE = 2;
    private static final int REQUEST_EDIT_GENDER = 3;
    private static final int REQUEST_EDIT_HEIGHT = 4;
    private static final int REQUEST_EDIT_BIRTH = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Mapping view
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtGender = findViewById(R.id.txtGender);
        txtHeight = findViewById(R.id.txtHeight);
        txtBirth = findViewById(R.id.txtBirth);

        btnEditName = findViewById(R.id.btnEditName);
        btnEditPhone = findViewById(R.id.btnEditPhone);
        btnEditGender = findViewById(R.id.btnEditGender);
        btnEditHeight = findViewById(R.id.btnEditHeight);
        btnEditBirth = findViewById(R.id.btnEditBirth);

        btnBack = findViewById(R.id.btnBack);

        // Khi nhấn Back, quay lại trang trước (Fragment)
        btnBack.setOnClickListener(v -> finish());


        // ============================
        // Mở từng activity chỉnh sửa
        // ============================

        btnEditName.setOnClickListener(v -> {
            Intent i = new Intent(this, InfoEditNameActivity.class);
            i.putExtra("ten", txtName.getText().toString().replace(" >", ""));
            startActivityForResult(i, REQUEST_EDIT_NAME);
        });

        btnEditPhone.setOnClickListener(v -> {
            Intent i = new Intent(this, InfoEditPhoneActivity.class);
            i.putExtra("sdt", txtPhone.getText().toString().replace(" >", ""));
            startActivityForResult(i, REQUEST_EDIT_PHONE);
        });

        btnEditGender.setOnClickListener(v -> {
            Intent i = new Intent(this, InfoEditGenderActivity.class);
            i.putExtra("gioiTinh", txtGender.getText().toString().replace(" >", ""));
            startActivityForResult(i, REQUEST_EDIT_GENDER);
        });

        btnEditHeight.setOnClickListener(v -> {
            Intent i = new Intent(this, InfoEditHeightActivity.class);
            i.putExtra("chieuCao", txtHeight.getText().toString().replace(" cm >", ""));
            startActivityForResult(i, REQUEST_EDIT_HEIGHT);
        });

        btnEditBirth.setOnClickListener(v -> {
            Intent i = new Intent(this, InfoEditYearActivity.class);
            i.putExtra("namSinh", txtBirth.getText().toString().replace(" >", ""));
            startActivityForResult(i, REQUEST_EDIT_BIRTH);
        });
    }

    // =============================
    // Nhận dữ liệu trả về
    // =============================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {

            case REQUEST_EDIT_NAME:
                String ten = data.getStringExtra("ten");
                txtName.setText(ten + " >");
                break;

            case REQUEST_EDIT_PHONE:
                String sdt = data.getStringExtra("sdt");
                txtPhone.setText(sdt + " >");
                break;

            case REQUEST_EDIT_GENDER:
                String gioiTinh = data.getStringExtra("gioiTinh");
                txtGender.setText(gioiTinh + " >");
                break;

            case REQUEST_EDIT_HEIGHT:
                int chieuCao = data.getIntExtra("chieuCao", 0);
                txtHeight.setText(chieuCao + " cm >");
                break;

            case REQUEST_EDIT_BIRTH:
                int birth = data.getIntExtra("namSinh", 0);
                txtBirth.setText(birth + " >");
                break;
        }
    }
}
