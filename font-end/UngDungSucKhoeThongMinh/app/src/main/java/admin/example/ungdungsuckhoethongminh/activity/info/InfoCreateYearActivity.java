package admin.example.ungdungsuckhoethongminh.activity.info;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.DTO.CreateTaiKhoanRequest;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.auth.AuthActivity;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import admin.example.ungdungsuckhoethongminh.network.TaiKhoanApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoCreateYearActivity extends AppCompatActivity {

    private static final String TAG = "InfoCreateYearActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen không có title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_info_create_year);

        EditText edtBirthYear = findViewById(R.id.edtBirthYear);
        Button btnSave = findViewById(R.id.btnSave);

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyAppData", MODE_PRIVATE);
        String phone = prefs.getString("phone", "");
        String name = prefs.getString("name", "");
        String gender = prefs.getString("gender", "Chưa chọn");
        int height = prefs.getInt("height", 0);
        float weight = prefs.getFloat("weight", 0);

        Log.d(TAG, "Dữ liệu trước khi tạo tài khoản:");
        Log.d(TAG, "Phone: " + phone);
        Log.d(TAG, "Tên: " + name);
        Log.d(TAG, "Giới tính: " + gender);
        Log.d(TAG, "Chiều cao: " + height);
        Log.d(TAG, "Cân nặng: " + weight);

        btnSave.setOnClickListener(v -> {
            String yearStr = edtBirthYear.getText().toString().trim();
            if (yearStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập năm sinh", Toast.LENGTH_SHORT).show();
                return;
            }

            int birthYear = Integer.parseInt(yearStr);

            // Lưu năm sinh vào SharedPreferences
            prefs.edit().putInt("birthYear", birthYear).apply();

            Log.d(TAG, "Năm sinh: " + birthYear);

            // Tạo đối tượng request gửi lên API
            CreateTaiKhoanRequest request = new CreateTaiKhoanRequest(
                    phone, name, gender, height, birthYear, weight
            );

            TaiKhoanApi api = ApiClient.getTaiKhoanApi();
            api.createTaiKhoan(request).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(InfoCreateYearActivity.this,
                                "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();

                        // Chuyển sang AuthActivity
                        startActivity(new Intent(InfoCreateYearActivity.this, AuthActivity.class));
                        finish(); // đóng activity hiện tại
                    } else {
                        Toast.makeText(InfoCreateYearActivity.this,
                                "Lỗi server: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Response error: " + response.code() + " - " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(InfoCreateYearActivity.this,
                            "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "API failure", t);
                }
            });
        });
    }
}
