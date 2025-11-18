package admin.example.ungdungsuckhoethongminh.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import admin.example.ungdungsuckhoethongminh.data.Repository;
import admin.example.ungdungsuckhoethongminh.model.AppData;
import admin.example.ungdungsuckhoethongminh.model.MucDoHoatDong;
import admin.example.ungdungsuckhoethongminh.model.ThongTinCanNang;
import admin.example.ungdungsuckhoethongminh.model.TocDoCanNang;

public class SharedViewModel extends AndroidViewModel {
    public MutableLiveData<AppData> appData = new MutableLiveData<>();
    public MutableLiveData<ThongTinCanNang> user = new MutableLiveData<>();

    private final Repository repo = new Repository();

    public SharedViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadDataFromAssets() {
        repo.loadFromAssets(getApplication().getApplicationContext(), new Repository.Callback() {
            @Override
            public void onLoaded(AppData data) {
                appData.setValue(data);
                if (data.thongTinCanNang != null && !data.thongTinCanNang.isEmpty()) {
                    user.setValue(data.thongTinCanNang.get(0));
                }
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    // update user's fields
    public void updateWeight(double w) {
        ThongTinCanNang u = user.getValue();
        if (u != null) {
            u.canNangHienTai = w;
            user.setValue(u);
        }
    }
    public void updateTargetWeight(double w) {
        ThongTinCanNang u = user.getValue();
        if (u != null) {
            u.canNangMucTieu = w;
            user.setValue(u);
        }
    }
    public void updateActivityId(int id) {
        ThongTinCanNang u = user.getValue();
        if (u != null) {
            u.idMucDoHoatDong = id;
            user.setValue(u);
        }
    }
    public void updatePaceId(String id) {
        ThongTinCanNang u = user.getValue();
        if (u != null) {
            u.idTocDoCanNang = id;
            user.setValue(u);
        }
    }

    // calculation helpers
    public int calcAge(int birthYear) {
        int y = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return y - birthYear;
    }

    public double calcBMR(ThongTinCanNang u) {
        if (u == null) return 0;
        int age = calcAge(u.namSinh);
        if ("Nam".equalsIgnoreCase(u.gioiTinh) || "male".equalsIgnoreCase(u.gioiTinh)) {
            return 10.0 * u.canNangHienTai + 6.25 * u.chieuCaoCm - 5.0 * age + 5.0;
        } else {
            return 10.0 * u.canNangHienTai + 6.25 * u.chieuCaoCm - 5.0 * age - 161.0;
        }
    }

    public int getActivityCalories(ThongTinCanNang u) {
        AppData d = appData.getValue();
        if (d == null || d.mucDoHoatDong == null || u == null) return 0;
        for (MucDoHoatDong m : d.mucDoHoatDong) {
            if (m.id == u.idMucDoHoatDong) return m.caloHoatDong;
        }
        return 0;
    }

    public int getDailyDeficit(ThongTinCanNang u) {
        AppData d = appData.getValue();
        if (d == null || d.tocDoCanNang == null || u == null) return 0;
        for (TocDoCanNang t : d.tocDoCanNang) {
            if (t.id.equals(u.idTocDoCanNang)) return t.caloThayDoiMoiNgay;
        }
        return 0;
    }

    public int calcTargetCalories() {
        ThongTinCanNang u = user.getValue();
        if (u == null) return 0;
        double bmr = calcBMR(u);
        int activity = getActivityCalories(u);
        int deficit = getDailyDeficit(u);
        double total;
        if (u.canNangMucTieu < u.canNangHienTai) {
            total = bmr + activity - deficit;
        } else if (u.canNangMucTieu > u.canNangHienTai) {
            total = bmr + activity + deficit;
        } else {
            total = bmr + activity;
        }
        return (int)Math.round(total);
    }
    public int getSignedDeficit() {
        ThongTinCanNang u = user.getValue();
        if (u == null) return 0;

        int deficit = getDailyDeficit(u);

        if (u.canNangMucTieu < u.canNangHienTai) {
            return -deficit;
        } else if (u.canNangMucTieu > u.canNangHienTai) {
            return deficit;
        } else {
            return 0;
        }
    }
}
