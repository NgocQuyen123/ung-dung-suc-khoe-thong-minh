package admin.example.ungdungsuckhoethongminh;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import admin.example.ungdungsuckhoethongminh.model.User;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import admin.example.ungdungsuckhoethongminh.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneActivity extends AppCompatActivity {

    EditText edtPhone;
    Button btnSave;
    ImageView imgClose;

    int userId = 1; // ID user cần update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);

        edtPhone = findViewById(R.id.edtPhone);
        btnSave = findViewById(R.id.btnSave);
        imgClose = findViewById(R.id.imgClose); // thêm ImageView nút Close

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

            updatePhoneToServer(phone);
        });
    }

    // =========================
    // NÚT CLOSE
    // =========================
    private void setupCloseButton() {
        imgClose.setOnClickListener(v -> {
            // Trả về RESULT_CANCELED nếu người dùng bấm Close
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    // =========================
    // CALL API UPDATE
    // =========================
    private void updatePhoneToServer(String phone) {

        ApiService api = ApiClient.getApiService();

        // Lấy thông tin user trước
        api.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {

                    User user = response.body();
                    user.setSdt(phone); // cập nhật số điện thoại

                    // PUT lên server
                    api.updateUser(userId, user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {

                                Toast.makeText(PhoneActivity.this,
                                        "Cập nhật thành công!",
                                        Toast.LENGTH_SHORT).show();

                                // gửi dữ liệu về ProfileActivity
                                Intent data = new Intent();
                                data.putExtra("sdt", phone);
                                setResult(RESULT_OK, data);

                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("API", "Lỗi cập nhật: " + t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("API", "Không thể lấy user: " + t.getMessage());
            }
        });
    }
}
