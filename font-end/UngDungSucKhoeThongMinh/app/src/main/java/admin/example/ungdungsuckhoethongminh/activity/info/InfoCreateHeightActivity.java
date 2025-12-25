package admin.example.ungdungsuckhoethongminh.activity.info;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;

import admin.example.ungdungsuckhoethongminh.R;

public class InfoCreateHeightActivity extends AppCompatActivity {

    private int currentHeight = 165;
    private final int minHeight = 100;
    private final int maxHeight = 250;

    private TextView txtHeightValue;
    private View indicator;
    private ConstraintLayout heightScaleContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_info_create_height);

        // Ánh xạ
        txtHeightValue = findViewById(R.id.txtHeightValue);
        indicator = findViewById(R.id.indicator);
        heightScaleContainer = findViewById(R.id.heightScaleContainer);

        FrameLayout iconLeft = findViewById(R.id.iconLeft);
        FrameLayout iconRight = findViewById(R.id.iconRight);
        Button btnSave = findViewById(R.id.btnSave);

        txtHeightValue.setText(String.valueOf(currentHeight));

        // Nút trừ
        iconLeft.setOnClickListener(v -> {
            if (currentHeight > minHeight) {
                currentHeight--;
                txtHeightValue.setText(String.valueOf(currentHeight));
                updateIndicator();
            }
        });

        // Nút cộng
        iconRight.setOnClickListener(v -> {
            if (currentHeight < maxHeight) {
                currentHeight++;
                txtHeightValue.setText(String.valueOf(currentHeight));
                updateIndicator();
            }
        });

        // Sự kiện kéo trên thanh scale
        heightScaleContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        handleTouch(event.getX());
                        return true;
                }
                return false;
            }
        });

        // Sự kiện kéo trực tiếp vạch hồng
        indicator.setOnTouchListener(new View.OnTouchListener() {
            private float startX = 0;
            private float startTranslationX = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startTranslationX = indicator.getTranslationX();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float deltaX = event.getRawX() - startX;
                        float newTranslationX = startTranslationX + deltaX;

                        // Giới hạn di chuyển trong container
                        float minX = 0;
                        float maxX = heightScaleContainer.getWidth() - indicator.getWidth();
                        newTranslationX = Math.max(minX, Math.min(maxX, newTranslationX));

                        indicator.setTranslationX(newTranslationX);

                        // Tính chiều cao tương ứng
                        float percent = newTranslationX / maxX;
                        currentHeight = (int) (minHeight + percent * (maxHeight - minHeight));
                        txtHeightValue.setText(String.valueOf(currentHeight));
                        return true;

                    case MotionEvent.ACTION_UP:
                        return true;
                }
                return false;
            }
        });

        // Lưu và chuyển màn hình
        btnSave.setOnClickListener(v -> {
            // Lưu currentHeight vào SharedPreferences
            SharedPreferences prefs = getSharedPreferences("MyAppData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("height", currentHeight); // lưu kiểu int
            editor.apply(); // commit dữ liệu

            // Chuyển sang màn hình tiếp theo
            Intent intent = new Intent(InfoCreateHeightActivity.this, InfoCreateYearActivity.class);
            startActivity(intent);
        });


        // Cập nhật vị trí ban đầu
        heightScaleContainer.post(new Runnable() {
            @Override
            public void run() {
                updateIndicator();
            }
        });
    }

    /**
     * Xử lý chạm vào thanh scale
     */
    private void handleTouch(float touchX) {
        float width = heightScaleContainer.getWidth();

        // Tính phần trăm vị trí chạm (0 → 1)
        float percent = touchX / width;
        percent = Math.max(0, Math.min(1, percent));

        // Tính chiều cao tương ứng
        currentHeight = (int) (minHeight + percent * (maxHeight - minHeight));
        txtHeightValue.setText(String.valueOf(currentHeight));

        updateIndicator();
    }

    /**
     * Cập nhật vị trí vạch hồng
     */
    private void updateIndicator() {
        float width = heightScaleContainer.getWidth();
        if (width == 0) return;

        float percent = (currentHeight - minHeight) / (float) (maxHeight - minHeight);
        float posX = width * percent;

        indicator.setTranslationX(posX - indicator.getWidth() / 2f);
    }
}