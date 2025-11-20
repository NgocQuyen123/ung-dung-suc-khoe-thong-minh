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

public class EditHeightActivity extends AppCompatActivity {

    private TextView txtHeightValue;
    private Button btnSave;
    private ImageView btnClose;
    private FrameLayout iconLeft, iconRight;

    private LinearLayout scaleTicksContainer;

    private int currentHeight;
    private int minHeight = 100;
    private int maxHeight = 250;

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

        // Nhận dữ liệu chiều cao từ Intent
        currentHeight = getIntent().getIntExtra("chieuCao", 165);
        txtHeightValue.setText(String.valueOf(currentHeight));

        // Nút đóng
        btnClose.setOnClickListener(v -> finish());

        // Nút giảm
        iconLeft.setOnClickListener(v -> {
            if (currentHeight > minHeight) {
                currentHeight--;
                txtHeightValue.setText(String.valueOf(currentHeight));
                updateScalePosition();
            }
        });

        // Nút tăng
        iconRight.setOnClickListener(v -> {
            if (currentHeight < maxHeight) {
                currentHeight++;
                txtHeightValue.setText(String.valueOf(currentHeight));
                updateScalePosition();
            }
        });

        // Kéo theo thước
        scaleTicksContainer.setOnTouchListener((v, event) -> {
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
        });

        updateScalePosition();

        // Lưu chiều cao (KHÔNG gọi API nữa)
        btnSave.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("chieuCao", currentHeight);
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }

    private void updateScalePosition() {
        int numberOfTicks = maxHeight - minHeight + 1;
        float tickWidth = scaleTicksContainer.getWidth() / (float) numberOfTicks;
        float offset = (currentHeight - minHeight) * tickWidth;

        scaleTicksContainer.setTranslationX(
                -offset + scaleTicksContainer.getWidth() / 2f - tickWidth / 2f
        );
    }
}
