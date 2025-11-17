package admin.example.ungdungsuckhoethongminh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.model.User;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtName, txtPhone, txtGender, txtHeight, txtBirth;
    private LinearLayout btnEditName, btnEditPhone, btnEditGender, btnEditHeight, btnEditBirth;

    private static final int REQUEST_EDIT_NAME = 1;
    private static final int REQUEST_EDIT_PHONE = 2;
    private static final int REQUEST_EDIT_BIRTH = 3;
    private static final int REQUEST_EDIT_GENDER = 4;
    private static final int REQUEST_EDIT_HEIGHT = 5;



    private User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Ánh xạ TextView
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtGender = findViewById(R.id.txtGender);
        txtHeight = findViewById(R.id.txtHeight);
        txtBirth = findViewById(R.id.txtBirth);

        // Ánh xạ LinearLayout để edit
        btnEditName = findViewById(R.id.btnEditName);
        btnEditPhone = findViewById(R.id.btnEditPhone);
        btnEditGender = findViewById(R.id.btnEditGender);
        btnEditHeight = findViewById(R.id.btnEditHeight);
        btnEditBirth = findViewById(R.id.btnEditBirth);

        // Load dữ liệu từ API
        loadUser();

        // ==========================
        // MỞ Update Name Activity
        // ==========================
        btnEditName.setOnClickListener(v -> {
            if (currentUser == null) return;

            Intent intent = new Intent(ProfileActivity.this, EditNameActivity.class);
            intent.putExtra("id", currentUser.getId());
            intent.putExtra("ten", currentUser.getTen());
            intent.putExtra("sdt", currentUser.getSdt());
            intent.putExtra("gioiTinh", currentUser.getGioiTinh());
            intent.putExtra("chieuCao", currentUser.getChieuCao());
            intent.putExtra("namSinh", currentUser.getNamSinh());

            startActivityForResult(intent, REQUEST_EDIT_NAME);
        });

        // ==========================
        // MỞ Update Phone Activity
        // ==========================
        btnEditPhone.setOnClickListener(v -> {
            if (currentUser == null) return;

            Intent intent = new Intent(ProfileActivity.this, PhoneActivity.class);
            intent.putExtra("id", currentUser.getId());
            intent.putExtra("ten", currentUser.getTen());
            intent.putExtra("sdt", currentUser.getSdt());
            intent.putExtra("gioiTinh", currentUser.getGioiTinh());
            intent.putExtra("chieuCao", currentUser.getChieuCao());
            intent.putExtra("namSinh", currentUser.getNamSinh());

            startActivityForResult(intent, REQUEST_EDIT_PHONE);
        });

        // ==========================
        // MỞ Update BirthYear Activity
        // ==========================
        btnEditBirth.setOnClickListener(v -> {
            if (currentUser == null) return;

            Intent intent = new Intent(ProfileActivity.this, EditDearActivity.class);
            intent.putExtra("id", currentUser.getId());
            intent.putExtra("ten", currentUser.getTen());
            intent.putExtra("sdt", currentUser.getSdt());
            intent.putExtra("gioiTinh", currentUser.getGioiTinh());
            intent.putExtra("chieuCao", currentUser.getChieuCao());
            intent.putExtra("namSinh", currentUser.getNamSinh());

            startActivityForResult(intent, REQUEST_EDIT_BIRTH);
        });
        // ==========================
// MỞ Update Gender Activity
// ==========================
        btnEditGender.setOnClickListener(v -> {
            if (currentUser == null) return;

            Intent intent = new Intent(ProfileActivity.this, EditGenderActivity.class);
            intent.putExtra("id", currentUser.getId());
            intent.putExtra("ten", currentUser.getTen());
            intent.putExtra("sdt", currentUser.getSdt());
            intent.putExtra("gioiTinh", currentUser.getGioiTinh());
            intent.putExtra("chieuCao", currentUser.getChieuCao());
            intent.putExtra("namSinh", currentUser.getNamSinh());

            startActivityForResult(intent, REQUEST_EDIT_GENDER);
        });
        btnEditHeight.setOnClickListener(v -> {
            if (currentUser == null) return;

            Intent intent = new Intent(ProfileActivity.this, EditHeightActivity.class);
            intent.putExtra("id", currentUser.getId());
            intent.putExtra("ten", currentUser.getTen());
            intent.putExtra("sdt", currentUser.getSdt());
            intent.putExtra("gioiTinh", currentUser.getGioiTinh());
            intent.putExtra("chieuCao", currentUser.getChieuCao());
            intent.putExtra("namSinh", currentUser.getNamSinh());

            startActivityForResult(intent, REQUEST_EDIT_HEIGHT);
        });


    }


    private void loadUser() {
        int userId = 1; // ID user bạn muốn lấy
        ApiClient.getApiService().getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentUser = response.body();
                    // Cập nhật UI
                    txtName.setText(currentUser.getTen() + " >");
                    txtPhone.setText(currentUser.getSdt() + " >");
                    txtGender.setText(currentUser.getGioiTinh() + " >");
                    txtHeight.setText(currentUser.getChieuCao() + " cm >");
                    txtBirth.setText(currentUser.getNamSinh() + " >");
                } else {
                    Toast.makeText(ProfileActivity.this, "Không lấy được thông tin user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        // =============================
        // Nhận tên mới
        // =============================
        if (requestCode == REQUEST_EDIT_NAME && resultCode == Activity.RESULT_OK) {
            String newTen = data.getStringExtra("ten");
            txtName.setText(newTen + " >");
            if (currentUser != null) {
                currentUser.setTen(newTen);
            }
        }

        // =============================
        // Nhận số điện thoại mới
        // =============================
        if (requestCode == REQUEST_EDIT_PHONE && resultCode == Activity.RESULT_OK) {
            String newPhone = data.getStringExtra("sdt");
            txtPhone.setText(newPhone + " >");
            if (currentUser != null) {
                currentUser.setSdt(newPhone);
            }
        }

        // =============================
        // Nhận năm sinh mới
        // =============================
        if (requestCode == REQUEST_EDIT_BIRTH && resultCode == Activity.RESULT_OK) {
            int newBirthYear = data.getIntExtra("namSinh", currentUser.getNamSinh());
            txtBirth.setText(newBirthYear + " >");
            if (currentUser != null) {
                currentUser.setNamSinh(newBirthYear);
            }
        }
        // =============================
// Nhận giới tính mới
// =============================
        if (requestCode == REQUEST_EDIT_GENDER && resultCode == Activity.RESULT_OK) {
            String newGender = data.getStringExtra("gioiTinh");
            txtGender.setText(newGender + " >");
            if (currentUser != null) {
                currentUser.setGioiTinh(newGender);
            }
        }
        // =============================
// Nhận chiều cao mới
// =============================
        if (requestCode == REQUEST_EDIT_HEIGHT && resultCode == Activity.RESULT_OK) {
            int newHeight = data.getIntExtra("chieuCao", currentUser.getChieuCao());
            txtHeight.setText(newHeight + " cm >");

            if (currentUser != null) {
                currentUser.setChieuCao(newHeight);
            }
        }


    }
}
