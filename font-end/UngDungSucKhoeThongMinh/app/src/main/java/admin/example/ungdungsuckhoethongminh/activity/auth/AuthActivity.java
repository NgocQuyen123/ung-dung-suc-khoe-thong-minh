package admin.example.ungdungsuckhoethongminh.activity.auth;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.signUp.SignUpMethodScreen;
import admin.example.ungdungsuckhoethongminh.activity.singIn.SignInMethodScreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import java.util.concurrent.TimeUnit;

import android.widget.TextView;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "GOOGLE_FIT";
    private static final int REQ_ACTIVITY_PERMISSION = 1001;
    private static final int REQ_GOOGLE_SIGN_IN = 1002;
    private static final int REQ_GOOGLE_FIT_PERMISSION = 1003;

    private FitnessOptions fitnessOptions;
    private GoogleSignInClient googleSignInClient;

    private Button btnSignIn, btnSignUp;
    private TextView tvStatus;
    private static final int REQUEST_FIT_PERMISSION = 1001;
    private static final int RC_SIGN_IN = 9001; // Request code for Google Sign-In

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // 1️⃣ Xin quyền Activity Recognition (BẮT BUỘC Android 10+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                        REQ_ACTIVITY_PERMISSION
                );
                return;
            }
        }

        setupGoogleFit();

        tvStatus = findViewById(R.id.tvStatus);

        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(v -> {
            startActivity(new Intent(AuthActivity.this, SignInMethodScreen.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
        });

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(AuthActivity.this, SignUpMethodScreen.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
        });
    }


    // 2️⃣ Cấu hình Google Fit + Google Sign In
    private void setupGoogleFit() {

        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .addExtension(fitnessOptions)
                        .build();

        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null) {
            // 3️⃣ Chưa login Google → login
            startActivityForResult(
                    googleSignInClient.getSignInIntent(),
                    REQ_GOOGLE_SIGN_IN
            );
        } else if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            // 4️⃣ Chưa có quyền Google Fit
            GoogleSignIn.requestPermissions(
                    this,
                    REQ_GOOGLE_FIT_PERMISSION,
                    account,
                    fitnessOptions
            );
        } else {
            // 5️⃣ OK → đọc dữ liệu
            readStepData(account);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == REQ_GOOGLE_SIGN_IN) {
                GoogleSignInAccount account =
                        GoogleSignIn.getSignedInAccountFromIntent(data)
                                .getResult(ApiException.class);

                Toast.makeText(this, "Đăng nhập Google thành công", Toast.LENGTH_SHORT).show();

                if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
                    GoogleSignIn.requestPermissions(
                            this,
                            REQ_GOOGLE_FIT_PERMISSION,
                            account,
                            fitnessOptions
                    );
                } else {
                    readStepData(account);
                }
            }

            if (requestCode == REQ_GOOGLE_FIT_PERMISSION && resultCode == RESULT_OK) {
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                readStepData(account);
            }

        } catch (Exception e) {
            Log.e(TAG, "Sign in error", e);
            Toast.makeText(this, "Lỗi đăng nhập Google", Toast.LENGTH_LONG).show();
        }
    }

    // 6️⃣ ĐỌC DỮ LIỆU BƯỚC CHÂN (CHUẨN – KHÔNG = 0)
    private void readStepData(GoogleSignInAccount account) {

        long endTime = System.currentTimeMillis();
        long startTime = endTime - TimeUnit.DAYS.toMillis(1);

        DataReadRequest request = new DataReadRequest.Builder()
                .aggregate(
                        DataType.TYPE_STEP_COUNT_DELTA,
                        DataType.AGGREGATE_STEP_COUNT_DELTA
                )
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .bucketByTime(1, TimeUnit.DAYS)
                .build();

        Fitness.getHistoryClient(this, account)
                .readData(request)
                .addOnSuccessListener(response -> {

                    int totalSteps = 0;

                    for (Bucket bucket : response.getBuckets()) {
                        for (DataSet dataSet : bucket.getDataSets()) {
                            for (DataPoint dp : dataSet.getDataPoints()) {
                                totalSteps += dp.getValue(Field.FIELD_STEPS).asInt();
                            }
                        }
                    }

                    Log.d(TAG, "Steps today = " + totalSteps);
                    Toast.makeText(
                            this,
                            "Số bước hôm nay: " + totalSteps,
                            Toast.LENGTH_LONG
                    ).show();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Read Google Fit failed", e);
                    Toast.makeText(
                            this,
                            "Lỗi đọc Google Fit: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }

    // 7️⃣ Kết quả xin permission2
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_ACTIVITY_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupGoogleFit();
            } else {
                Toast.makeText(
                        this,
                        "Cần quyền Activity Recognition để đọc bước chân",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }


}
