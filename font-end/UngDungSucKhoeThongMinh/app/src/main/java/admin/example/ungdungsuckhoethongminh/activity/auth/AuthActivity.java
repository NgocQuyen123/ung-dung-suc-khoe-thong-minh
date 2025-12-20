package admin.example.ungdungsuckhoethongminh.activity.auth;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.signUp.SignUpMethodScreen;
import admin.example.ungdungsuckhoethongminh.activity.singIn.SignInMethodScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import android.widget.TextView;

public class AuthActivity extends AppCompatActivity {

    private Button btnSignIn, btnSignUp;
    private TextView tvStatus;
    private static final int REQUEST_FIT_PERMISSION = 1001;
    private static final int RC_SIGN_IN = 9001; // Request code for Google Sign-In
    private FitnessOptions fitnessOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        tvStatus = findViewById(R.id.tvStatus);

        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        // Check if user is already signed in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            // User is not signed in, request sign-in
            signIn();
        } else {
            // User is already signed in, request Google Fit permission
            requestGoogleFitPermission(account);
        }

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

    // Request Google Sign-In
    private void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id)) // Add your client ID from Google Cloud Console
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    // Handle the result of the Google Sign-In request and permissions request
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Xử lý kết quả đăng nhập Google
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                // Lấy tài khoản Google đã đăng nhập thành công
                GoogleSignInAccount account = task.getResult();
                if (account != null) {
                    String accountName = account.getDisplayName(); // Tên tài khoản
                    String accountEmail = account.getEmail(); // Email tài khoản

                    // In ra Logcat để kiểm tra thông tin tài khoản
                    Log.d("GoogleSignIn", "Account Name: " + accountName);
                    Log.d("GoogleSignIn", "Account Email: " + accountEmail);

                    updateStatus("Đăng nhập thành công với tài khoản: " + accountName + " (" + accountEmail + ")");
                    requestGoogleFitPermission(account);
                } else {
                    Log.e("GoogleSignIn", "Không tìm thấy tài khoản Google hợp lệ.");
                    updateStatus("Không tìm thấy tài khoản Google hợp lệ.");
                }
            } else {
                // In lỗi nếu đăng nhập thất bại
                Log.e("GoogleSignIn", "Google Sign-In failed: " + task.getException());
                updateStatus("Google Sign-In failed.");
            }
        }
    }



    // Request Google Fit permissions after sign-in
    private void requestGoogleFitPermission(GoogleSignInAccount account) {
        if (account == null) {
            updateStatus("No valid Google account found.");
            return;
        }

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            Toast.makeText(this, "App needs Google Fit permissions. Please grant permission.", Toast.LENGTH_LONG).show();
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_FIT_PERMISSION,
                    account,
                    fitnessOptions
            );
        } else {
            getAccessToken(account);
        }
    }

    // Retrieve access token using the Google account and credentials
    private void getAccessToken(GoogleSignInAccount account) {
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        this,
                        Collections.singleton("https://www.googleapis.com/auth/fitness.activity.read")
                );

        if (account.getAccount() != null) {
            credential.setSelectedAccount(account.getAccount());
        } else {
            updateStatus("Invalid Google account.");
            return;
        }

        new Thread(() -> {
            try {
                String accessToken = credential.getToken();
                if (accessToken == null || accessToken.isEmpty()) {
                    runOnUiThread(() -> updateStatus("Empty token!"));
                } else {
                    runOnUiThread(() -> updateStatus("Token successfully retrieved!"));
                    sendTokenToBackend(accessToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> updateStatus("Failed to retrieve token: " + e.getMessage()));
            }
        }).start();
    }

    // Send the access token to the backend server
    private void sendTokenToBackend(String token) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("accessToken", token);
            json.put("startTime", System.currentTimeMillis() - 86400000);
            json.put("endTime", System.currentTimeMillis());
        } catch (Exception ignored) {}

        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                // Thay đổi URL từ localhost thành IP cục bộ
                .url("http://192.168.100.187:8080/api/fitness/steps")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                runOnUiThread(() -> updateStatus("Token sent successfully: " + res));
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("BackendRequest", "Error sending token: " + e.getMessage());
                runOnUiThread(() -> updateStatus("Failed to send token: " + e.getMessage()));
            }

        });
    }


    // Update status and show toast
    private void updateStatus(String message) {
        tvStatus.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
