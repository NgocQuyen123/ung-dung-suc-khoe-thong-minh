package admin.example.ungdungsuckhoethongminh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Button btnSignIn,btnSignUp;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignInMethodScreen.class);
            startActivity(intent);

            // animation chuyển màn (nếu có file anim)
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
        });

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpMethodScreen.class);
            startActivity(intent);
            // animation chuyển màn (nếu muốn)
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
        });
    }
}