package admin.example.ungdungsuckhoethongminh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.model.User;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import admin.example.ungdungsuckhoethongminh.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditNameActivity extends AppCompatActivity {

    private EditText edtName;
    private Button btnSave;
    private ImageView btnBack;

    private ApiService apiService;

    private int userId;
    private User currentUser;   // lưu user đầy đủ để update PUT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        edtName = findViewById(R.id.edtName);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.imgClose);

        apiService = ApiClient.getApiService();

        // Nhận dữ liệu từ Intent
        userId = getIntent().getIntExtra("id", 1);

        currentUser = new User(
                userId,
                getIntent().getStringExtra("ten"),
                getIntent().getStringExtra("sdt"),
                getIntent().getStringExtra("gioiTinh"),
                getIntent().getIntExtra("chieuCao", 0),
                getIntent().getIntExtra("namSinh", 0)
        );

        edtName.setText(currentUser.getTen());

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String newTen = edtName.getText().toString().trim();
            if (newTen.isEmpty()) {
                Toast.makeText(EditNameActivity.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cập nhật tên vào user đầy đủ
            currentUser.setTen(newTen);

            apiService.updateUser(userId, currentUser).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {

                        // Trả dữ liệu mới về ProfileActivity
                        Intent data = new Intent();
                        data.putExtra("ten", newTen);
                        setResult(Activity.RESULT_OK, data);

                        Toast.makeText(EditNameActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditNameActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(EditNameActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
