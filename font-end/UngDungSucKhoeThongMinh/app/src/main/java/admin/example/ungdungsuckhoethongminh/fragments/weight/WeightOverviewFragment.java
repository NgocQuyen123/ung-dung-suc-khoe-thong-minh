package admin.example.ungdungsuckhoethongminh.fragments.weight;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import admin.example.ungdungsuckhoethongminh.model.CanNangTuanModel;
import admin.example.ungdungsuckhoethongminh.model.CanNangThangModel;
import admin.example.ungdungsuckhoethongminh.model.CanNangNamModel;
import admin.example.ungdungsuckhoethongminh.model.NgayCanNangModel;
import admin.example.ungdungsuckhoethongminh.model.CanNangTuanTrongThangModel;
import admin.example.ungdungsuckhoethongminh.model.CanNangThangTrongNamModel;

import admin.example.ungdungsuckhoethongminh.view.WeightChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.weight.WeightHostActivity;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;
import admin.example.ungdungsuckhoethongminh.network.CanNangApi;

public class WeightOverviewFragment extends Fragment {

    // ===== ENUM CHẾ ĐỘ =====
    private enum CheDo {
        TUAN, THANG, NAM
    }
    private CanNangApi canNangApi;
    private TabLayout tabCanNang;
    private TextView tvThoiGian, tvCanNangTrungBinh;



    private CheDo cheDoHienTai = CheDo.TUAN;
    private LocalDate mocThoiGian = LocalDate.now();

    // ===== VIEW =====
    private Button btnTabTuan, btnTabThang, btnTabNam;
    private ImageView ivPrev, ivNext, ivBack;
    private TextView tvThoiGianCanNang, tvCanNangTrungBinhTitle, tvCanNangMucTieu;
    private LinearLayout thoiGianChiTiet;
    private ConstraintLayout clWeightInput;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        canNangApi = ApiClient.getCanNangApi();
        anhXa(view);
        xuLySuKien();
        capNhatTrangThaiTab();
        taiDuLieu(); // mặc định TUẦN
    }

    // ================= ÁNH XẠ =================
    private void anhXa(View view) {
        btnTabTuan = view.findViewById(R.id.btnTabTuan);
        btnTabThang = view.findViewById(R.id.btnTabThang);
        btnTabNam = view.findViewById(R.id.btnTabNam);

        ivPrev = view.findViewById(R.id.ivPrevWeek);
        ivNext = view.findViewById(R.id.ivNextWeek);
        ivBack = view.findViewById(R.id.ivBack);

        tvThoiGianCanNang = view.findViewById(R.id.tvThoiGianCanNang);
        tvCanNangTrungBinhTitle = view.findViewById(R.id.tvCanNangTrungBinhTitle);
        tvCanNangTrungBinh = view.findViewById(R.id.tvCanNangTrungBinh);
        tvCanNangMucTieu = view.findViewById(R.id.tvCanNangMucTieu);

        thoiGianChiTiet = view.findViewById(R.id.thoiGianChiTiet);
        clWeightInput = view.findViewById(R.id.clWeightInput);
    }

    // ================= SỰ KIỆN =================
    private void xuLySuKien() {

        btnTabTuan.setOnClickListener(v -> {
            cheDoHienTai = CheDo.TUAN;
            mocThoiGian = LocalDate.now();
            capNhatTrangThaiTab();
            taiDuLieu();
        });

        btnTabThang.setOnClickListener(v -> {
            cheDoHienTai = CheDo.THANG;
            mocThoiGian = LocalDate.now();
            capNhatTrangThaiTab();
            taiDuLieu();
        });

        btnTabNam.setOnClickListener(v -> {
            cheDoHienTai = CheDo.NAM;
            mocThoiGian = LocalDate.now();
            capNhatTrangThaiTab();
            taiDuLieu();
        });

        ivPrev.setOnClickListener(v -> {
            if (cheDoHienTai == CheDo.TUAN) mocThoiGian = mocThoiGian.minusWeeks(1);
            else if (cheDoHienTai == CheDo.THANG) mocThoiGian = mocThoiGian.minusMonths(1);
            else mocThoiGian = mocThoiGian.minusYears(1);

            taiDuLieu();
        });

        ivNext.setOnClickListener(v -> {
            if (cheDoHienTai == CheDo.TUAN) mocThoiGian = mocThoiGian.plusWeeks(1);
            else if (cheDoHienTai == CheDo.THANG) mocThoiGian = mocThoiGian.plusMonths(1);
            else mocThoiGian = mocThoiGian.plusYears(1);

            taiDuLieu();
        });

        clWeightInput.setOnClickListener(v -> {
            if (getActivity() instanceof WeightHostActivity) {
                ((WeightHostActivity) getActivity())
                        .navigateTo(new WeightAddFragment(), true);
            }
        });

        ivBack.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    // ================= TAB =================
    private void capNhatTrangThaiTab() {
        resetTab(btnTabTuan);
        resetTab(btnTabThang);
        resetTab(btnTabNam);
        Button btn =
                cheDoHienTai == CheDo.TUAN ? btnTabTuan :
                        cheDoHienTai == CheDo.THANG ? btnTabThang :
                                btnTabNam;
        btn.setBackgroundResource(R.drawable.btn_next);
        btn.setTextColor(Color.WHITE);

        btn.setBackgroundResource(R.drawable.btn_next);
        btn.setTextColor(Color.WHITE);
    }

    private void resetTab(Button btn) {
        btn.setBackgroundColor(Color.TRANSPARENT);
        btn.setTextColor(Color.BLACK);
    }

    // ================= LOAD DATA =================
    private void taiDuLieu() {
        switch (cheDoHienTai) {
            case TUAN:
                taiCanNangTheoTuan();
                break;
            case THANG:
                taiCanNangTheoThang();
                break;
            case NAM:
                taiCanNangTheoNam();
                break;
        }
    }

//    private void taiCanNangTheoTuan() {
//        canNangApi.layCanNangTheoTuan(mocThoiGian.toString())
//                .enqueue(new Callback<CanNangTuanModel>() {
//                    @Override
//                    public void onResponse(Call<CanNangTuanModel> call,
//                                           Response<CanNangTuanModel> response) {
//                        if (!isAdded() || response.body() == null) return;
//                        View rootView = getView();
//                        if (rootView == null) return;
//                        CanNangTuanModel duLieu = response.body();
//
//                        tvThoiGianCanNang.setText(duLieu.label);
//                        tvCanNangTrungBinhTitle.setText("TRUNG BÌNH TUẦN");
//                        tvCanNangTrungBinh.setText(formatCanNang(duLieu.trungBinh));
//
//                        tvCanNangMucTieu.setText("-- kg"); // mục tiêu làm sau
//                        WeightChartView chart = rootView.findViewById(R.id.weightChart);
//                        List<String> labels = List.of("T2","T3","T4","T5","T6","T7","CN");
//                        List<Double> values = new ArrayList<>();
//                        values.add(60.0); values.add(60.0); values.add(60.0); values.add(60.0); values.add(60.0); values.add(60.0); values.add(60.0);
//                        // Trục thời gian: luôn 7 ngày
//                        capNhatThoiGianChiTiet(labels);
//                        chart.setData(values, labels);
//
//                        // 👉 duLieu.ngay dùng để VẼ BIỂU ĐỒ NGÀY sau này
//                    }
//
//                    @Override
//                    public void onFailure(Call<CanNangTuanModel> call, Throwable t) {
//                        Log.e("API_DEBUG","TUAN API failure: " + t.getMessage(), t);
//                        capNhatThoiGianChiTiet(List.of("T2","T3","T4","T5","T6","T7","CN"));
//                    }
//                });
//    }
//    private void taiCanNangTheoThang() {
//        canNangApi.layCanNangTheoThang(
//                mocThoiGian.getMonthValue(),
//                mocThoiGian.getYear()
//        ).enqueue(new Callback<CanNangThangModel>() {
//            @Override
//            public void onResponse(Call<CanNangThangModel> call,
//                                   Response<CanNangThangModel> response) {
//                Log.d("API_DEBUG", "TUAN response: " + response.body());
//                if (!isAdded() || response.body() == null) {
//                    Log.e("API_DEBUG","Body null hoặc fragment chưa attach");
//                    return;
//                }
//
//                CanNangThangModel duLieu = response.body();
//
//                tvThoiGianCanNang.setText(duLieu.label);
//                tvCanNangTrungBinhTitle.setText("TRUNG BÌNH THÁNG");
//                tvCanNangTrungBinh.setText(formatCanNang(duLieu.trungBinh));
//
//                // 👉 Trục thời gian: TUẦN
//                List<String> labels = List.of("1","7","14","21","28");
//                capNhatThoiGianChiTiet(labels);
//
//                // 👉 duLieu.tuan dùng để VẼ BIỂU ĐỒ TUẦN
//            }
//
//            @Override
//            public void onFailure(Call<CanNangThangModel> call, Throwable t) {
//                capNhatThoiGianChiTiet(List.of("1","7","14","21","28"));
//            }
//        });
//    }
//    private void taiCanNangTheoNam() {
//        canNangApi.layCanNangTheoNam(mocThoiGian.getYear())
//                .enqueue(new Callback<CanNangNamModel>() {
//                    @Override
//                    public void onResponse(Call<CanNangNamModel> call,
//                                           Response<CanNangNamModel> response) {
//                        if (!isAdded() || response.body() == null) return;
//
//                        CanNangNamModel duLieu = response.body();
//                        Log.d("DEBUG_NAM", "response.body() = " + duLieu);
//                        if (duLieu != null) {
//                            Log.d("DEBUG_NAM", "label = " + duLieu.label);
//                            Log.d("DEBUG_NAM", "danhSachThang size = " + (duLieu.danhSachThang != null ? duLieu.danhSachThang.size() : "null"));
//                            if (duLieu.danhSachThang != null) {
//                                for (CanNangThangTrongNamModel thang : duLieu.danhSachThang) {
//                                    Log.d("DEBUG_NAM", "thang: " + thang.thang + ", trungBinhThang: " + thang.trungBinhThang);
//                                }
//                            }
//                        }
//                        tvThoiGianCanNang.setText(duLieu.label);
//                        tvCanNangTrungBinhTitle.setText("TRUNG BÌNH NĂM");
//                        Double trungBinhNam = null;
//                        if (duLieu.danhSachThang != null && !duLieu.danhSachThang.isEmpty()) {
//                            double sum = 0;
//                            int count = 0;
//                            for (CanNangThangTrongNamModel thang : duLieu.danhSachThang) {
//                                if (thang != null && thang.trungBinhThang != null) {
//                                    sum += thang.trungBinhThang;
//                                    count++;
//                                }
//                            }
//                            trungBinhNam = count > 0 ? sum / count : null;
//                        }
//
//                        tvCanNangTrungBinh.setText(formatCanNang(trungBinhNam));
//
//                        // 👉 Trục thời gian: THÁNG
//                        // Trục thời gian: 12 tháng luôn
//                        List<String> labels = new ArrayList<>();
//                        for (int i = 1; i <= 12; i++) {
//                            labels.add(String.valueOf(i));
//                        }
//                        capNhatThoiGianChiTiet(labels);
//
//                        // 👉 duLieu.thangTrongNam dùng để VẼ BIỂU ĐỒ THÁNG
//                    }
//
//                    @Override
//                    public void onFailure(Call<CanNangNamModel> call, Throwable t) {
//                        Log.e("API_DEBUG","NAM API failure: " + t.getMessage(), t);
//                        // Nếu API fail, vẫn tạo trục mặc định
//                        List<String> labels = new ArrayList<>();
//                        for (int i = 1; i <= 12; i++) labels.add("T" + i);
//                        capNhatThoiGianChiTiet(labels);
//                    }
//                });
//    }
private void taiCanNangTheoTuan() {
    canNangApi.layCanNangTheoTuan(mocThoiGian.toString())
            .enqueue(new Callback<CanNangTuanModel>() {
                @Override
                public void onResponse(Call<CanNangTuanModel> call, Response<CanNangTuanModel> response) {
                    if (!isAdded()) return;
                    View root = getView();
                    WeightChartView chart = root != null ? root.findViewById(R.id.weightChart) : null;
                    if (response.body() == null) return;

                    CanNangTuanModel duLieu = response.body();
                    tvThoiGianCanNang.setText(duLieu.label);
                    tvCanNangTrungBinh.setText(formatCanNang(duLieu.trungBinh));

                    // 1. Luôn tạo 7 nhãn cố định
                    List<String> labels = List.of("T2","T3","T4","T5","T6","T7","CN");

                    // 2. Khởi tạo mảng giá trị với 7 phần tử null
                    List<Double> values = new ArrayList<>();
                    for (int i = 0; i < 7; i++) values.add(null);

                    // 3. Đổ dữ liệu từ API vào đúng vị trí
                    if (duLieu.danhSachNgay != null) {
                        for (NgayCanNangModel item : duLieu.danhSachNgay) {
                            try {
                                LocalDate d = LocalDate.parse(item.ngay);
                                // getDayOfWeek: Monday=1 ... Sunday=7
                                int idx = d.getDayOfWeek().getValue() - 1;
                                if (idx >= 0 && idx < 7) {
                                    values.set(idx, item.canNang);
                                }
                            } catch (Exception e) {
                                Log.e("API_ERROR", "Parse error: " + item.ngay);
                            }
                        }
                    }

                    capNhatThoiGianChiTiet(labels);
                    if (chart != null) {
                        chart.setData(values, labels);
                    }
                }
                @Override
                public void onFailure(Call<CanNangTuanModel> call, Throwable t) {}
            });
}

    private void taiCanNangTheoThang() {
        canNangApi.layCanNangTheoThang(mocThoiGian.getMonthValue(), mocThoiGian.getYear())
                .enqueue(new Callback<CanNangThangModel>() {
                    @Override
                    public void onResponse(Call<CanNangThangModel> call, Response<CanNangThangModel> response) {
                        if (!isAdded()) return;
                        View root = getView();
                        WeightChartView chart = root != null ? root.findViewById(R.id.weightChart) : null;
                        if (response.body() == null) return;

                        CanNangThangModel duLieu = response.body();
                        tvThoiGianCanNang.setText(duLieu.label);
                        tvCanNangTrungBinhTitle.setText("TRUNG BÌNH THÁNG");
                        tvCanNangTrungBinh.setText(formatCanNang(duLieu.trungBinh));

                        // 1. Nhãn cố định 5 cột
                        List<String> labels = List.of("1", "7", "14", "21", "28");

                        // 2. Khởi tạo values có 5 phần tử null
                        List<Double> values = new ArrayList<>();
                        for (int i = 0; i < 5; i++) values.add(null);

                        // 3. Đổ dữ liệu trung bình tuần vào đúng vị trí cột dựa trên thứ tự mảng
                        // API trả về mảng "tuan", ta lấy tuần thứ i gán vào cột thứ i
                        if (duLieu.danhSachTuan != null) {
                            for (int i = 0; i < duLieu.danhSachTuan.size() && i < 5; i++) {
                                // Ép giá trị trung bình tuần vào vị trí cột i
                                values.set(i, duLieu.danhSachTuan.get(i).trungBinhTuan);
                            }
                        }

                        capNhatThoiGianChiTiet(labels);
                        if (chart != null) {
                            chart.setData(values, labels);
                        }
                    }

                    @Override
                    public void onFailure(Call<CanNangThangModel> call, Throwable t) {
                        Log.e("API_DEBUG", "Thang fail: " + t.getMessage());
                    }
                });
    }

    private void taiCanNangTheoNam() {
        canNangApi.layCanNangTheoNam(mocThoiGian.getYear())
                .enqueue(new Callback<CanNangNamModel>() {
                    @Override
                    public void onResponse(Call<CanNangNamModel> call, Response<CanNangNamModel> response) {
                        if (!isAdded()) return;
                        View root = getView();
                        WeightChartView chart = root != null ? root.findViewById(R.id.weightChart) : null;

                        if (response.body() == null) {
                            List<String> labels = new ArrayList<>();
                            for (int i = 1; i <= 12; i++) labels.add(String.valueOf(i));
                            List<Double> values = new ArrayList<>();
                            for (int i = 0; i < labels.size(); i++) values.add(null);
                            capNhatThoiGianChiTiet(labels);
                            if (chart != null) chart.setData(values, labels);
                            return;
                        }

                        CanNangNamModel duLieu = response.body();
                        tvThoiGianCanNang.setText(duLieu.label);
                        tvCanNangTrungBinhTitle.setText("TRUNG BÌNH NĂM");

                        Double trungBinhNam = null;
                        if (duLieu.danhSachThang != null && !duLieu.danhSachThang.isEmpty()) {
                            double sum = 0; int count = 0;
                            for (CanNangThangTrongNamModel thang : duLieu.danhSachThang) {
                                if (thang != null && thang.trungBinhThang != null) {
                                    sum += thang.trungBinhThang; count++;
                                }
                            }
                            trungBinhNam = count > 0 ? sum / count : null;
                        }
                        tvCanNangTrungBinh.setText(formatCanNang(trungBinhNam));

                        List<String> labels = new ArrayList<>();
                        for (int i = 1; i <= 12; i++) labels.add(String.valueOf(i));
                        List<Double> values = new ArrayList<>();
                        for (int i = 0; i < 12; i++) values.add(null);

                        if (duLieu.danhSachThang != null) {
                            for (CanNangThangTrongNamModel thang : duLieu.danhSachThang) {
                                int m = thang.thang; // 1..12
                                Double v = thang.trungBinhThang;
                                if (m >= 1 && m <= 12) values.set(m - 1, v);
                            }
                        }

                        capNhatThoiGianChiTiet(labels);
                        if (chart != null) chart.setData(values, labels);
                    }

                    @Override
                    public void onFailure(Call<CanNangNamModel> call, Throwable t) {
                        List<String> labels = new ArrayList<>();
                        for (int i = 1; i <= 12; i++) labels.add(String.valueOf(i));
                        List<Double> values = new ArrayList<>();
                        for (int i = 0; i < 12; i++) values.add(null);
                        capNhatThoiGianChiTiet(labels);
                        View root = getView();
                        if (root != null) {
                            WeightChartView chart = root.findViewById(R.id.weightChart);
                            if (chart != null) chart.setData(values, labels);
                        }
                    }
                });
    }

    private String formatCanNang(Double canNang) {
        if (canNang == null) return "-- kg";
        return String.format("%.1f kg", canNang);
    }
    // ================= TRỤC THỜI GIAN =================
    private void capNhatThoiGianChiTiet(List<String> labels) {
        thoiGianChiTiet.removeAllViews(); // xóa hết TextView cũ
        thoiGianChiTiet.setWeightSum(labels.size()); // set lại weightSum theo số phần tử

        for (String label : labels) {
            TextView tv = new TextView(requireContext());
            tv.setText(label);
            tv.setTextSize(14);
            tv.setTextColor(Color.DKGRAY);
            tv.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tv.setLayoutParams(lp);

            thoiGianChiTiet.addView(tv);
        }
    }
}
