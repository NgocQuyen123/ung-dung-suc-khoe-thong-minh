package admin.example.ungdungsuckhoethongminh.activity.info;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        // ===== init view =====
        btnEditName = findViewById(R.id.btnEditName);
        btnEditPhone = findViewById(R.id.btnEditPhone);
        btnEditGender = findViewById(R.id.btnEditGender);
        btnEditHeight = findViewById(R.id.btnEditHeight);
        btnEditBirth = findViewById(R.id.btnEditBirth);

        btnBack = findViewById(R.id.btnBack);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtGender = findViewById(R.id.txtGender);
        txtHeight = findViewById(R.id.txtHeight);
        txtBirth = findViewById(R.id.txtBirth);

        infoManager = new InfoManager(this);

        btnBack.setOnClickListener(v -> finish());

        // ===== LOAD USER INFO =====
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
        // ví dụ userId = 1 (thường lấy từ login)
        int userId = 1;

        infoManager.loadUser(userId, user -> {
            txtName.setText(user.getTenTK());
            txtEmail.setText(user.getEmail());
            txtGender.setText(user.getGioiTinh());
            txtHeight.setText(user.getChieuCao() + " cm");
            txtBirth.setText(String.valueOf(user.getNamSinh()));
        });
    }
}
