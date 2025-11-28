package admin.example.ungdungsuckhoethongminh.activity.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;

public class InfoProfileActivity extends AppCompatActivity {

    private LinearLayout btnEditName, btnEditPhone, btnEditGender, btnEditHeight, btnEditBirth;
    private ImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        btnEditName = findViewById(R.id.btnEditName);
        btnEditPhone = findViewById(R.id.btnEditPhone);
        btnEditGender = findViewById(R.id.btnEditGender);
        btnEditHeight = findViewById(R.id.btnEditHeight);
        btnEditBirth = findViewById(R.id.btnEditBirth);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());


        btnEditName.setOnClickListener(v -> {
            Intent i = new Intent(this, InfoEditNameActivity.class);
            startActivity(i);
        });


        btnEditPhone.setOnClickListener(v -> {
            Intent i = new Intent(this, InfoEditPhoneActivity.class);
            startActivity(i);
        });

        btnEditGender.setOnClickListener(v -> {
            Intent i = new Intent(this, InfoEditGenderActivity.class);
            startActivity(i);
        });

        btnEditHeight.setOnClickListener(v -> {
            Intent i = new Intent(this, InfoEditHeightActivity.class);
            startActivity(i);
        });

        btnEditBirth.setOnClickListener(v -> {
            Intent i = new Intent(this, InfoEditYearActivity.class);
            startActivity(i);
        });

    }

}
