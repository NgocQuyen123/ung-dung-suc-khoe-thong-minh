package admin.example.ungdungsuckhoethongminh.activity.plan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;

public class PlanWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_workout);

        // Xử lý nút XEM CHI TIẾT
        TextView btnWeekly = findViewById(R.id.btnWeekly);

        btnWeekly.setOnClickListener(v -> {
            Intent intent = new Intent(PlanWorkoutActivity.this, PlanScheduleListActivity.class);
            startActivity(intent);
        });
    }
}
