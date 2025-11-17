package admin.example.ungdungsuckhoethongminh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class EditDearActivity extends AppCompatActivity {

    private EditText edtBirthYear;
    private Button btnSave;
    private ImageView btnClose;

    private ApiService apiService;

    private int userId;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_year); // layout mới

        edtBirthYear = findViewById(R.id.edtBirthYear);
        btnSave = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.imgClose);

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

        // Hiển thị năm sinh hiện tại
        int birthYear = currentUser.getNamSinh();
        edtBirthYear.setText(birthYear > 0 ? String.valueOf(birthYear) : "");

        // Nút close
        btnClose.setOnClickListener(v -> finish());

        // Nút lưu
        btnSave.setOnClickListener(v -> {
            String newYearStr = edtBirthYear.getText().toString().trim();
            if (newYearStr.isEmpty()) {
                Toast.makeText(EditDearActivity.this, "Vui lòng nhập năm sinh", Toast.LENGTH_SHORT).show();
                return;
            }

            int newYear;
            try {
                newYear = Integer.parseInt(newYearStr);
            } catch (NumberFormatException e) {
                Toast.makeText(EditDearActivity.this, "Năm sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            currentUser.setNamSinh(newYear);

            apiService.updateUser(userId, currentUser).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Intent data = new Intent();
                        data.putExtra("namSinh", newYear);
                        setResult(Activity.RESULT_OK, data);

                        Toast.makeText(EditDearActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditDearActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(EditDearActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
