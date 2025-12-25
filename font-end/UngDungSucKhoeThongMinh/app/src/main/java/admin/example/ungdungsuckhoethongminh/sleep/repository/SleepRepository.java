package admin.example.ungdungsuckhoethongminh.sleep.repository;

import androidx.annotation.NonNull;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.sleep.api.SleepApi;
import admin.example.ungdungsuckhoethongminh.sleep.api.SleepApiClient;
import admin.example.ungdungsuckhoethongminh.sleep.model.SleepNgayPoint;
import admin.example.ungdungsuckhoethongminh.sleep.model.SleepThangPoint;
import admin.example.ungdungsuckhoethongminh.sleep.model.SleepThangAvg;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SleepRepository {

    public interface ResultCallback<T> {
        void onSuccess(@NonNull T data);

        void onError(@NonNull Throwable t);
    }

    private static RuntimeException httpError(@NonNull Response<?> response) {
        String msg = "HTTP " + response.code();
        try {
            if (response.errorBody() != null) {
                msg += ": " + response.errorBody().string();
            }
        } catch (Exception ignored) {
        }
        return new RuntimeException(msg);
    }

    private final SleepApi api;

    public SleepRepository() {
        this.api = SleepApiClient.get().create(SleepApi.class);
    }

    public void getNgay(int idTaiKhoan,
                        @NonNull String ngayYYYYMMDD,
                        @NonNull ResultCallback<List<SleepNgayPoint>> cb) {
        api.getNgay(idTaiKhoan, "ngay", ngayYYYYMMDD).enqueue(wrapNgay(cb));
    }

    public void getTuan(int idTaiKhoan,
                        @NonNull String ngayYYYYMMDD,
                        @NonNull ResultCallback<List<SleepNgayPoint>> cb) {
        api.getTuan(idTaiKhoan, "tuan", ngayYYYYMMDD).enqueue(wrapNgay(cb));
    }

    public void getThang(int idTaiKhoan,
                         int nam,
                         int thang,
                         @NonNull ResultCallback<List<SleepNgayPoint>> cb) {
        api.getThang(idTaiKhoan, "thang", nam, thang).enqueue(wrapNgay(cb));
    }

    public void getNamTong(int idTaiKhoan,
                           int nam,
                           @NonNull ResultCallback<List<SleepThangPoint>> cb) {
        api.getNamTong(idTaiKhoan, "nam_tong", nam).enqueue(new Callback<List<SleepThangPoint>>() {
            @Override
            public void onResponse(@NonNull Call<List<SleepThangPoint>> call,
                                   @NonNull Response<List<SleepThangPoint>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cb.onSuccess(response.body());
                } else {
                    cb.onError(httpError(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SleepThangPoint>> call, @NonNull Throwable t) {
                cb.onError(t);
            }
        });
    }

    public void getNam(int idTaiKhoan,
                       int nam,
                       @NonNull ResultCallback<List<SleepThangAvg>> cb) {
        api.getNam(idTaiKhoan, "nam", nam).enqueue(new Callback<List<SleepThangAvg>>() {
            @Override
            public void onResponse(@NonNull Call<List<SleepThangAvg>> call,
                                   @NonNull Response<List<SleepThangAvg>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cb.onSuccess(response.body());
                } else {
                    cb.onError(httpError(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SleepThangAvg>> call, @NonNull Throwable t) {
                cb.onError(t);
            }
        });
    }

    private static Callback<List<SleepNgayPoint>> wrapNgay(@NonNull ResultCallback<List<SleepNgayPoint>> cb) {
        return new Callback<List<SleepNgayPoint>>() {
            @Override
            public void onResponse(@NonNull Call<List<SleepNgayPoint>> call,
                                   @NonNull Response<List<SleepNgayPoint>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cb.onSuccess(response.body());
                } else {
                    cb.onError(httpError(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SleepNgayPoint>> call, @NonNull Throwable t) {
                cb.onError(t);
            }
        };
    }
}
