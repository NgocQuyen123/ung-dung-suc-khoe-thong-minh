package admin.example.ungdungsuckhoethongminh.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.signUp.SignUpMethodScreen;
import admin.example.ungdungsuckhoethongminh.activity.singIn.SignInMethodScreen;


public class AuthActivity extends AppCompatActivity {
    private Button btnSignIn,btnSignUp;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, SignInMethodScreen.class);
            startActivity(intent);

            // animation chuyển màn (nếu có file anim)
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
        });

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, SignUpMethodScreen.class);
            startActivity(intent);
            // animation chuyển màn (nếu muốn)
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
        });
    }
}