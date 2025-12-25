package admin.example.ungdungsuckhoethongminh.activity.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import admin.example.ungdungsuckhoethongminh.R;

public class InfoCreateNameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_create_name);

        EditText edtName = findViewById(R.id.edtName);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Lưu tên vào SharedPreferences
            SharedPreferences prefs = getSharedPreferences("MyAppData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("name", name);
            editor.apply();

            Intent intent = new Intent(InfoCreateNameActivity.this, InfoCreateGenderActivity.class);
            startActivity(intent);
        });
    }
}