package admin.example.ungdungsuckhoethongminh.activity.info;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.info.util.InfoManager;
import admin.example.ungdungsuckhoethongminh.model.TaiKhoan;

public class InfoProfileActivity extends AppCompatActivity {

    private LinearLayout btnEditName, btnEditPhone, btnEditGender, btnEditHeight, btnEditBirth;
    private ImageView btnBack;
    private TextView txtName, txtSdt, txtGender, txtHeight, txtBirth;
    private InfoManager infoManager;

    private TaiKhoan currentUser;
    private int userId;

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
        txtSdt = findViewById(R.id.txtSdt);
        txtGender = findViewById(R.id.txtGender);
        txtHeight = findViewById(R.id.txtHeight);
        txtBirth = findViewById(R.id.txtBirth);

        infoManager = new InfoManager(this);

        btnBack.setOnClickListener(v -> finish());

        // Lấy userId từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyAppData", MODE_PRIVATE);
        userId = prefs.getInt("userId", 1);

        // Load thông tin user từ server hoặc local
        loadUserInfo();

        // ===== NAVIGATION =====
        btnEditName.setOnClickListener(v -> {
            Intent intent = new Intent(this, InfoEditNameActivity.class);
            intent.putExtra("tenTK", txtName.getText().toString());
            intent.putExtra("id", userId);
            startActivityForResult(intent, 1000);
        });

        btnEditPhone.setOnClickListener(v -> {
            Intent intent = new Intent(this, InfoEditPhoneActivity.class);
            intent.putExtra("sdt", txtSdt.getText().toString());
            intent.putExtra("id", userId);
            startActivityForResult(intent, 1001);
        });

        btnEditGender.setOnClickListener(v -> {
            Intent intent = new Intent(this, InfoEditGenderActivity.class);
            intent.putExtra("gioiTinh", txtGender.getText().toString());
            intent.putExtra("id", userId);
            startActivityForResult(intent, 1002);
        });

        btnEditHeight.setOnClickListener(v -> {
            Intent intent = new Intent(this, InfoEditHeightActivity.class);
            intent.putExtra("chieuCao", txtHeight.getText().toString().replace(" cm",""));
            intent.putExtra("id", userId);
            startActivityForResult(intent, 1003);
        });

        btnEditBirth.setOnClickListener(v -> {
            Intent intent = new Intent(this, InfoEditYearActivity.class);
            intent.putExtra("namSinh", txtBirth.getText().toString());
            intent.putExtra("id", userId);
            startActivityForResult(intent, 1004);
        });
    }

    private void loadUserInfo() {
        Log.d("USER_ID", "ID tài khoản: " + userId);
        infoManager.loadUser(userId, new InfoManager.OnUserLoaded() {
            @Override
            public void onSuccess(TaiKhoan user) {
                currentUser = user; // Lưu lại object hiện tại
                updateUI();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(InfoProfileActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Cập nhật UI từ currentUser
    private void updateUI() {
        if (currentUser != null) {
            txtName.setText(currentUser.getTenTK());
            txtSdt.setText(currentUser.getSdt());
            txtGender.setText(currentUser.getGioiTinh());
            txtHeight.setText(currentUser.getChieuCao() + " cm");
            txtBirth.setText(String.valueOf(currentUser.getNamSinh()));
        }
    }

    // Xử lý dữ liệu trả về từ các Activity edit
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || currentUser == null) return;

        switch (requestCode) {
            case 1000: // Name
                if (resultCode == RESULT_OK) {
                    String newName = data.getStringExtra("tenTK");
                    if (newName != null && !newName.isEmpty()) {
                        currentUser.setTenTK(newName);
                        txtName.setText(newName);
                        updateUser(currentUser);
                    }
                }
                break;
            case 1001: // Phone
                if (resultCode == RESULT_OK) {
                    String newPhone = data.getStringExtra("sdt");
                    if (newPhone != null && !newPhone.isEmpty()) {
                        currentUser.setSdt(newPhone);
                        txtSdt.setText(newPhone);
                        updateUser(currentUser);
                    }
                }
                break;
            case 1002: // Gender
                if (resultCode == RESULT_OK) {
                    String newGender = data.getStringExtra("gioiTinh");
                    if (newGender != null && !newGender.isEmpty()) {
                        currentUser.setGioiTinh(newGender);
                        txtGender.setText(newGender);
                        updateUser(currentUser);
                    }
                }
                break;
            case 1003: // Height
                if (resultCode == RESULT_OK) {
                    int newHeight = data.getIntExtra("chieuCao", 0);
                    if (newHeight > 0) {
                        currentUser.setChieuCao(newHeight);
                        txtHeight.setText(newHeight + " cm");
                        updateUser(currentUser);
                    }
                }
                break;
            case 1004: // Birth year
                if (resultCode == RESULT_OK) {
                    int newBirthYear = data.getIntExtra("namSinh", 0);
                    if (newBirthYear > 0) {
                        currentUser.setNamSinh(newBirthYear);
                        txtBirth.setText(String.valueOf(newBirthYear));
                        updateUser(currentUser);
                    }
                }
                break;
        }
    }

    // Gọi API cập nhật user
    private void updateUser(TaiKhoan user) {
        infoManager.updateUser(user, new InfoManager.OnUserUpdated() {
            @Override
            public void onSuccess(TaiKhoan updatedUser) {
                Toast.makeText(InfoProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(InfoProfileActivity.this, "Cập nhật thất bại: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
