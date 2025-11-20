package admin.example.ungdungsuckhoethongminh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditNameActivity extends AppCompatActivity {

    private EditText edtName;
    private Button btnSave;
    private ImageView btnBack;

    private String currentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        edtName = findViewById(R.id.edtName);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.imgClose);

        currentName = getIntent().getStringExtra("ten");
        if (currentName == null) currentName = "";

        edtName.setText(currentName);

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String newTen = edtName.getText().toString().trim();

            Intent data = new Intent();
            data.putExtra("ten", newTen);
            setResult(Activity.RESULT_OK, data);

            finish();
        });
    }
}
