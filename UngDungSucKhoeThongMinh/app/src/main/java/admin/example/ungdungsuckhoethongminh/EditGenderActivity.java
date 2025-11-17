package admin.example.ungdungsuckhoethongminh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import admin.example.ungdungsuckhoethongminh.model.User;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import admin.example.ungdungsuckhoethongminh.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGenderActivity extends AppCompatActivity {

    private ConstraintLayout flagContainer1, flagContainer2;
    private TextView txtMale, txtFemale;
    private Button btnSave;
    private ImageView btnClose;

    private String selectedGender = null;

    private ApiService apiService;
    private int userId;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_indentity);

        // Ánh xạ view
        flagContainer1 = findViewById(R.id.flagContainer1);
        flagContainer2 = findViewById(R.id.flagContainer2);
        txtMale = findViewById(R.id.textView4);
        txtFemale = findViewById(R.id.textView5);
        btnSave = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.imgClose); // NÚT CLOSE

        apiService = ApiClient.getApiService();

        // Nhận dữ liệu từ ProfileActivity
        userId = getIntent().getIntExtra("id", 1);

        currentUser = new User(
                userId,
                getIntent().getStringExtra("ten"),
                getIntent().getStringExtra("sdt"),
                getIntent().getStringExtra("gioiTinh"),
                getIntent().getIntExtra("chieuCao", 0),
                getIntent().getIntExtra("namSinh", 0)
        );

        selectedGender = currentUser.getGioiTinh();
        highlightSelected();

        // XỬ LÝ NÚT CLOSE
        btnClose.setOnClickListener(v -> finish());

        // Chọn Nam
        flagContainer1.setOnClickListener(v -> {
            selectedGender = "Nam";
            highlightSelected();
        });

        // Chọn Nữ
        flagContainer2.setOnClickListener(v -> {
            selectedGender = "Nữ";
            highlightSelected();
        });

        // Lưu giới tính
        btnSave.setOnClickListener(v -> {
            if (selectedGender == null) {
                Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                return;
            }

            currentUser.setGioiTinh(selectedGender);

            apiService.updateUser(userId, currentUser).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Intent data = new Intent();
                        data.putExtra("gioiTinh", selectedGender);
                        setResult(Activity.RESULT_OK, data);
                        finish();
                    } else {
                        Toast.makeText(EditGenderActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(EditGenderActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void highlightSelected() {
        flagContainer1.setBackgroundResource(R.drawable.input_bg_nocl);
        flagContainer2.setBackgroundResource(R.drawable.input_bg_nocl);

        if ("Nam".equals(selectedGender)) {
            flagContainer1.setBackgroundResource(R.drawable.input_bg);
        } else if ("Nữ".equals(selectedGender)) {
            flagContainer2.setBackgroundResource(R.drawable.input_bg);
        }
    }
}
