package admin.example.ungdungsuckhoethongminh.activity.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import admin.example.ungdungsuckhoethongminh.R;

public class InfoCreateNameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_create_name);

        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            Intent intent = new Intent(InfoCreateNameActivity.this, InfoCreateYearActivity.class);
            startActivity(intent);
        });
    }
}