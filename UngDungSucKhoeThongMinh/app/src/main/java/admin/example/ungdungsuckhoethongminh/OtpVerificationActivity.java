package admin.example.ungdungsuckhoethongminh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText[] otpInputs = new EditText[6];

    private final String FIXED_OTP = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        // Gán 6 ô vào mảng
        otpInputs[0] = findViewById(R.id.etOtp1);
        otpInputs[1] = findViewById(R.id.etOtp2);
        otpInputs[2] = findViewById(R.id.etOtp3);
        otpInputs[3] = findViewById(R.id.etOtp4);
        otpInputs[4] = findViewById(R.id.etOtp5);
        otpInputs[5] = findViewById(R.id.etOtp6);

        setupNumberKeyboard();

        // Hiển thị OTP để test dễ hơn
        Toast.makeText(this, "Nhập OTP: " + FIXED_OTP, Toast.LENGTH_LONG).show();
    }

    // Gán sự kiện bàn phím 0–9
    private void setupNumberKeyboard() {
        setKeyListener(R.id.btnKey0, "0");
        setKeyListener(R.id.btnKey1, "1");
        setKeyListener(R.id.btnKey2, "2");
        setKeyListener(R.id.btnKey3, "3");
        setKeyListener(R.id.btnKey4, "4");
        setKeyListener(R.id.btnKey5, "5");
        setKeyListener(R.id.btnKey6, "6");
        setKeyListener(R.id.btnKey7, "7");
        setKeyListener(R.id.btnKey8, "8");
        setKeyListener(R.id.btnKey9, "9");

        findViewById(R.id.btnDelete).setOnClickListener(v -> deleteLastDigit());
    }

    private void setKeyListener(int layoutId, String number) {
        LinearLayout btn = findViewById(layoutId);
        btn.setOnClickListener(v -> fillNextInput(number));
    }

    private void fillNextInput(String value) {
        for (EditText edt : otpInputs) {
            if (edt.getText().toString().isEmpty()) {
                edt.setText(value);
                break;
            }
        }

        if (isOtpFilled()) verifyOtp();
    }

    private void deleteLastDigit() {
        for (int i = otpInputs.length - 1; i >= 0; i--) {
            if (!otpInputs[i].getText().toString().isEmpty()) {
                otpInputs[i].setText("");
                break;
            }
        }
    }

    private boolean isOtpFilled() {
        for (EditText edt : otpInputs) {
            if (edt.getText().toString().isEmpty()) return false;
        }
        return true;
    }

    private void verifyOtp() {
        StringBuilder userOtp = new StringBuilder();
        for (EditText edt : otpInputs) {
            userOtp.append(edt.getText().toString());
        }

        if (userOtp.toString().equals(FIXED_OTP)) {
            Toast.makeText(this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();

            // → Chuyển về màn Main
            Intent intent = new Intent(OtpVerificationActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Sai mã OTP!", Toast.LENGTH_SHORT).show();
            clearOtpInputs();
        }
    }

    private void clearOtpInputs() {
        for (EditText edt : otpInputs) edt.setText("");
    }
}