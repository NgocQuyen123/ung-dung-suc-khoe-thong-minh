package admin.example.ungdungsuckhoethongminh.info.util;

import android.content.Context;
import android.util.Log;

import admin.example.ungdungsuckhoethongminh.info.repository.InfoRepository;
import admin.example.ungdungsuckhoethongminh.info.session.UserSession;
import admin.example.ungdungsuckhoethongminh.model.TaiKhoan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoManager {

    private final InfoRepository repository;
    private final UserSession session;

    // Callback khi load user
    public interface OnUserLoaded {
        void onSuccess(TaiKhoan user);
        void onError(String message);
    }

    // Callback khi update user
    public interface OnUserUpdated {
        void onSuccess(TaiKhoan updatedUser);
        void onError(String message);
    }

    public InfoManager(Context context) {
        repository = new InfoRepository();
        session = new UserSession(context);
    }

    /**
     * Lấy user:
     * 1. Ưu tiên lấy từ Session
     * 2. Nếu chưa có thì gọi API
     */
    public void loadUser(int userId, OnUserLoaded callback) {

        // 🔹 Lấy từ cache
        TaiKhoan cachedUser = session.getUser();
        if (cachedUser != null) {
            callback.onSuccess(cachedUser);
            return;
        }

        // 🔹 Gọi API
        repository.fetchTaiKhoan(userId, new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TaiKhoan user = response.body();
                    session.saveUser(user);
                    callback.onSuccess(user);
                } else {
                    callback.onError("Không lấy được thông tin tài khoản");
                }
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                Log.e("InfoManager", "API lỗi", t);
                callback.onError("Lỗi kết nối server");
            }
        });
    }

    /**
     * Cập nhật user và lưu lại session
     */
    public void updateUser(TaiKhoan user, OnUserUpdated callback) {
        repository.updateTaiKhoan(user, new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TaiKhoan updatedUser = response.body();
                    session.saveUser(updatedUser); // cập nhật session
                    callback.onSuccess(updatedUser);
                } else {
                    callback.onError("Cập nhật thất bại");
                }
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                callback.onError("Lỗi kết nối server: " + t.getMessage());
            }
        });
    }

    public TaiKhoan getCurrentUser() {
        return session.getUser();
    }

    public void logout() {
        session.clear();
    }
}