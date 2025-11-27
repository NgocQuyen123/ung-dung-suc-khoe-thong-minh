package admin.example.ungdungsuckhoethongminh.activity.workout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;

public class AddScheduleActivity extends AppCompatActivity {

    ImageView imgClose, imgMore;
    TextView txtDate, txtWorkoutValue, txtDifficultyValue, txtDifficultyValue1, txtDifficultyValue3;
    NumberPicker npHour, npMinute, npAmPm;
    LinearLayout itemWorkout, itemDifficulty, itemReps, itemWeights;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        // 👉 sửa "your_layout_name" thành đúng tên XML của bạn
        // ví dụ: setContentView(R.layout.activity_add_schedule);

        initViews();
        setupNumberPickers();
        setupClickEvents();
    }

    private void initViews() {
        imgClose = findViewById(R.id.imgClose);
        imgMore = findViewById(R.id.imgMore);
        txtDate = findViewById(R.id.txtDate);

        npHour = findViewById(R.id.npHour);
        npMinute = findViewById(R.id.npMinute);
        npAmPm = findViewById(R.id.npAmPm);

        itemWorkout = findViewById(R.id.itemWorkout);
        itemDifficulty = findViewById(R.id.itemDifficulty);
        itemReps = findViewById(R.id.itemReps);
        itemWeights = findViewById(R.id.itemWeights);

        txtWorkoutValue = findViewById(R.id.txtWorkoutValue);
        txtDifficultyValue = findViewById(R.id.txtDifficultyValue);
        txtDifficultyValue1 = findViewById(R.id.txtDifficultyValue1);
        txtDifficultyValue3 = findViewById(R.id.txtDifficultyValue3);

        btnSave = findViewById(R.id.btnSave);
    }

    private void setupNumberPickers() {

        // ---- Giờ ----
        npHour.setMinValue(1);
        npHour.setMaxValue(12);
        npHour.setValue(10);

        // ---- Phút ----
        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npMinute.setFormatter(value -> String.format("%02d", value));

        // ---- AM / PM ----
        String[] ampm = {"AM", "PM"};
        npAmPm.setDisplayedValues(ampm);
        npAmPm.setMinValue(0);
        npAmPm.setMaxValue(1);
        npAmPm.setValue(1); // PM
    }

    private void setupClickEvents() {

        // Đóng Activity
        imgClose.setOnClickListener(v -> finish());

        // More menu
        imgMore.setOnClickListener(v ->
                Toast.makeText(this, "More options...", Toast.LENGTH_SHORT).show()
        );

        // Chọn Workout
        itemWorkout.setOnClickListener(v ->
                Toast.makeText(this, "Chọn bài tập luyện", Toast.LENGTH_SHORT).show()
        );

        // Chọn độ khó
        itemDifficulty.setOnClickListener(v ->
                Toast.makeText(this, "Chọn độ khó", Toast.LENGTH_SHORT).show()
        );

        // Tuỳ chỉnh số lần
        itemReps.setOnClickListener(v ->
                Toast.makeText(this, "Tuỳ chỉnh số lần", Toast.LENGTH_SHORT).show()
        );

        // Tuỳ chỉnh trọng lượng
        itemWeights.setOnClickListener(v ->
                Toast.makeText(this, "Tuỳ chỉnh trọng lượng", Toast.LENGTH_SHORT).show()
        );

        // Lưu lịch
        btnSave.setOnClickListener(v -> {
            String time = npHour.getValue() + ":"
                    + String.format("%02d", npMinute.getValue()) + " "
                    + (npAmPm.getValue() == 0 ? "AM" : "PM");

            Toast.makeText(this, "Đã lưu lịch lúc " + time, Toast.LENGTH_LONG).show();
        });
    }
}
