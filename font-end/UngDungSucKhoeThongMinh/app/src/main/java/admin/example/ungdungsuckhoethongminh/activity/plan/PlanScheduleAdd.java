package admin.example.ungdungsuckhoethongminh.activity.plan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView; // Import ImageView

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;

public class PlanScheduleAdd extends AppCompatActivity {

    private ImageView imgClose;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        // 1. Ánh xạ (tìm kiếm) ImageView
        imgClose = findViewById(R.id.imgClose);

        // 2. Thiết lập OnClickListener
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}