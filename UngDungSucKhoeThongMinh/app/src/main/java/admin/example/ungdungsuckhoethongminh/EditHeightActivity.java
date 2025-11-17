package admin.example.ungdungsuckhoethongminh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.model.User;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import admin.example.ungdungsuckhoethongminh.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditHeightActivity extends AppCompatActivity {

    private TextView txtHeightValue;
    private Button btnSave;
    private ImageView btnClose;
    private FrameLayout iconLeft, iconRight;

    private LinearLayout scaleTicksContainer;

    private int currentHeight;
    private int minHeight = 100;
    private int maxHeight = 250;

    private ApiService apiService;
    private int userId;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_height);

        txtHeightValue = findViewById(R.id.txtHeightValue);
        btnSave = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.imgClose);
        iconLeft = findViewById(R.id.iconLeft);
        iconRight = findViewById(R.id.iconRight);
        scaleTicksContainer = findViewById(R.id.scaleTicks);

        apiService = ApiClient.getApiService();

        // Nhận dữ liệu từ ProfileActivity
        userId = getIntent().getIntExtra("id", 1);

        currentUser = new User(
                userId,
                getIntent().getStringExtra("ten"),
                getIntent().getStringExtra("sdt"),
                getIntent().getStringExtra("gioiTinh"),
                getIntent().getIntExtra("chieuCao", 165),
                getIntent().getIntExtra("namSinh", 0)
        );

        currentHeight = currentUser.getChieuCao();
        txtHeightValue.setText(String.valueOf(currentHeight));

        btnClose.setOnClickListener(v -> finish());

        // Tăng / giảm bằng nút
        iconLeft.setOnClickListener(v -> {
            if (currentHeight > minHeight) {
                currentHeight--;
                txtHeightValue.setText(String.valueOf(currentHeight));
                updateScalePosition();
            }
        });

        iconRight.setOnClickListener(v -> {
            if (currentHeight < maxHeight) {
                currentHeight++;
                txtHeightValue.setText(String.valueOf(currentHeight));
                updateScalePosition();
            }
        });

        // Sự kiện kéo thước
        scaleTicksContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        float touchX = event.getX();
                        float centerX = scaleTicksContainer.getWidth() / 2f;
                        float distanceFromCenter = centerX - touchX;

                        int numberOfTicks = maxHeight - minHeight + 1;
                        float tickWidth = scaleTicksContainer.getWidth() / (float) numberOfTicks;

                        int deltaTicks = Math.round(distanceFromCenter / tickWidth);
                        int newHeight = currentHeight + deltaTicks;

                        if (newHeight < minHeight) newHeight = minHeight;
                        if (newHeight > maxHeight) newHeight = maxHeight;

                        currentHeight = newHeight;
                        txtHeightValue.setText(String.valueOf(currentHeight));

                        updateScalePosition();
                        return true;
                }
                return false;
            }
        });

        updateScalePosition();

        // Lưu chiều cao
        btnSave.setOnClickListener(v -> {
            currentUser.setChieuCao(currentHeight);

            apiService.updateUser(userId, currentUser).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Intent data = new Intent();
                        data.putExtra("chieuCao", currentHeight);
                        setResult(Activity.RESULT_OK, data);
                        finish();
                    } else {
                        Toast.makeText(EditHeightActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(EditHeightActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void updateScalePosition() {
        int numberOfTicks = maxHeight - minHeight + 1;
        float tickWidth = scaleTicksContainer.getWidth() / (float) numberOfTicks;
        float offset = (currentHeight - minHeight) * tickWidth;
        scaleTicksContainer.setTranslationX(-offset + scaleTicksContainer.getWidth() / 2f - tickWidth / 2f);
    }
}
