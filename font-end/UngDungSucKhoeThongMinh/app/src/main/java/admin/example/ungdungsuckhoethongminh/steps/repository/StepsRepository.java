package admin.example.ungdungsuckhoethongminh.steps.repository;

import androidx.annotation.NonNull;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.steps.api.BuocChanApi;
import admin.example.ungdungsuckhoethongminh.steps.api.StepsApiClient;
import admin.example.ungdungsuckhoethongminh.steps.model.BuocChanNgayPoint;
import admin.example.ungdungsuckhoethongminh.steps.model.BuocChanThangPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StepsRepository {

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

    private final BuocChanApi api;

    public StepsRepository() {
        this.api = StepsApiClient.get().create(BuocChanApi.class);
    }

    public void getNgay(int idTaiKhoan,
                        @NonNull String ngayYYYYMMDD,
                        @NonNull ResultCallback<List<BuocChanNgayPoint>> cb) {
        api.getNgay(idTaiKhoan, "ngay", ngayYYYYMMDD).enqueue(wrapNgay(cb));
    }

    public void getTuan(int idTaiKhoan,
                        @NonNull String ngayYYYYMMDD,
                        @NonNull ResultCallback<List<BuocChanNgayPoint>> cb) {
        api.getTuan(idTaiKhoan, "tuan", ngayYYYYMMDD).enqueue(wrapNgay(cb));
    }

    public void getThang(int idTaiKhoan,
                         int nam,
                         int thang,
                         @NonNull ResultCallback<List<BuocChanNgayPoint>> cb) {
        api.getThang(idTaiKhoan, "thang", nam, thang).enqueue(wrapNgay(cb));
    }

    public void getNam(int idTaiKhoan,
                       int nam,
                       @NonNull ResultCallback<List<BuocChanThangPoint>> cb) {
        api.getNam(idTaiKhoan, "nam", nam).enqueue(new Callback<List<BuocChanThangPoint>>() {
            @Override
            public void onResponse(@NonNull Call<List<BuocChanThangPoint>> call,
                                   @NonNull Response<List<BuocChanThangPoint>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cb.onSuccess(response.body());
                } else {
                    cb.onError(httpError(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BuocChanThangPoint>> call, @NonNull Throwable t) {
                cb.onError(t);
            }
        });
    }

    private static Callback<List<BuocChanNgayPoint>> wrapNgay(@NonNull ResultCallback<List<BuocChanNgayPoint>> cb) {
        return new Callback<List<BuocChanNgayPoint>>() {
            @Override
            public void onResponse(@NonNull Call<List<BuocChanNgayPoint>> call,
                                   @NonNull Response<List<BuocChanNgayPoint>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cb.onSuccess(response.body());
                } else {
                    cb.onError(httpError(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BuocChanNgayPoint>> call, @NonNull Throwable t) {
                cb.onError(t);
            }
        };
    }
}
