package admin.example.ungdungsuckhoethongminh.activity.info;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.info.util.InfoManager;

public class InfoProfileActivity extends AppCompatActivity {

    private LinearLayout btnEditName, btnEditPhone, btnEditGender, btnEditHeight, btnEditBirth;
    private ImageView btnBack;

    private TextView txtName, txtEmail, txtGender, txtHeight, txtBirth;

    private InfoManager infoManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // ===== INIT VIEW =====
        btnEditName = findViewById(R.id.btnEditName);
        btnEditPhone = findViewById(R.id.btnEditPhone);
        btnEditGender = findViewById(R.id.btnEditGender);
        btnEditHeight = findViewById(R.id.btnEditHeight);
        btnEditBirth = findViewById(R.id.btnEditBirth);

        btnBack = findViewById(R.id.btnBack);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtSdt);
        txtGender = findViewById(R.id.txtGender);
        txtHeight = findViewById(R.id.txtHeight);
        txtBirth = findViewById(R.id.txtBirth);

        infoManager = new InfoManager(this);

        btnBack.setOnClickListener(v -> finish());


        loadUserInfo();

        // ===== NAVIGATION =====
        btnEditName.setOnClickListener(v ->
                startActivity(new Intent(this, InfoEditNameActivity.class)));

        btnEditPhone.setOnClickListener(v ->
                startActivity(new Intent(this, InfoEditPhoneActivity.class)));

        btnEditGender.setOnClickListener(v ->
                startActivity(new Intent(this, InfoEditGenderActivity.class)));

        btnEditHeight.setOnClickListener(v ->
                startActivity(new Intent(this, InfoEditHeightActivity.class)));

        btnEditBirth.setOnClickListener(v ->
                startActivity(new Intent(this, InfoEditYearActivity.class)));
    }

    private void loadUserInfo() {

        // 👉 Thực tế userId nên lấy từ Login hoặc Session
        int userId = 1;

        infoManager.loadUser(userId, new InfoManager.OnUserLoaded() {
            @Override
            public void onSuccess(TaiKhoan user) {
                txtName.setText(user.getTenTK());
                txtSdt.setText(user.getSdt());
                txtGender.setText(user.getGioiTinh());
                txtHeight.setText(user.getChieuCao() + " cm");
                txtBirth.setText(String.valueOf(user.getNamSinh()));
            }

            @Override
            public void onError(String message) {
                Toast.makeText(InfoProfileActivity.this,
                        message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load lại khi quay về từ màn hình chỉnh sửa
        loadUserInfo();
    }
}
