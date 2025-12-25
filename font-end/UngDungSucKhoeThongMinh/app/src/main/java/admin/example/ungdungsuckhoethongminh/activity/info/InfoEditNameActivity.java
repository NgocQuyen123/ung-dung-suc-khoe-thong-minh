
package admin.example.ungdungsuckhoethongminh.activity.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;

public class InfoEditNameActivity extends AppCompatActivity {

    private EditText edtName;
    private Button btnSave;
    private ImageView btnBack;

    private String currentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_edit_name);

        edtName = findViewById(R.id.edtName);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.imgClose);

        currentName = getIntent().getStringExtra("tenTK");
        if (currentName == null) currentName = "";

        edtName.setText(currentName);

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String newTen = edtName.getText().toString().trim();

            Intent data = new Intent();
            data.putExtra("tenTK", newTen);
            setResult(Activity.RESULT_OK, data);

            finish();
        });
    }
}
