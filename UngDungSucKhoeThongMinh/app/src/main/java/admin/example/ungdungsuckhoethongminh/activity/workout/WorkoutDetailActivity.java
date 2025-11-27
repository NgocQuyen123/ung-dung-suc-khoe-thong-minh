package admin.example.ungdungsuckhoethongminh.activity.workout;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.databinding.ActivityEditGinalBinding;

public class WorkoutDetailActivity extends AppCompatActivity {

    private ActivityEditGinalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditGinalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupListeners();
        loadDefaultData();
    }

    private void loadDefaultData() {
        binding.txtTitle.setText("Tập luyện toàn thân");
        binding.txtTitle1.setText("Tập luyện toàn thân");

        binding.txtExercisesCount.setText("12");
        binding.txtTime.setText("30:00");
        binding.txtCalories.setText("260");

        binding.txtEquipmentValue.setText("Thứ hai, 03/2025");
        binding.txtFocusValue.setText("Hòa Khánh");

        binding.txtWarmup1Name.setText("Nâng tạ");
        binding.txtWarmup1Duration13.setText("0:30");

        binding.txtWarmup1Name1.setText("Căng người");
        binding.txtWarmup1Duration12.setText("0:30");

        binding.txtWarmup1Name16.setText("Gập bụng");
        binding.txtWarmup1Duration14.setText("0:30");
    }

    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnEdit.setOnClickListener(v ->
                showMessage("Edit tiêu đề bài tập")
        );

        binding.imgEquipmentArrow.setOnClickListener(v ->
                showMessage("Chọn thiết bị")
        );

        binding.imgFocusArrow.setOnClickListener(v ->
                showMessage("Chọn vùng tập trung")
        );

        binding.btnWarmup1Info1.setOnClickListener(v ->
                showMessage("Chi tiết bài: Căng người")
        );

        binding.btnWarmup1Info13.setOnClickListener(v ->
                showMessage("Chi tiết bài: Nâng tạ")
        );

        binding.btnWarmup1Info2.setOnClickListener(v ->
                showMessage("Chi tiết bài: Gập bụng")
        );

        binding.btnWarmupEdit.setOnClickListener(v ->
                showMessage("Chỉnh sửa danh sách warmup")
        );

        binding.btnWarmupEdit1.setOnClickListener(v ->
                showMessage("Chỉnh sửa danh sách tập luyện")
        );

        binding.btnSave.setOnClickListener(v ->
                showMessage("Đã lưu, chuẩn bị bắt đầu!")
        );
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
