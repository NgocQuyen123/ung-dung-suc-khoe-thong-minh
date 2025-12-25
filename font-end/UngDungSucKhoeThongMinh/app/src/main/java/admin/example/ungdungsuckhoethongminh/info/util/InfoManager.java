package admin.example.ungdungsuckhoethongminh.info.util;

import android.content.Context;
import android.util.Log;

import admin.example.ungdungsuckhoethongminh.info.repository.InfoRepository;
import admin.example.ungdungsuckhoethongminh.info.session.UserSession;
import admin.example.ungdungsuckhoethongminh.model.TaiKhoanInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoManager {

    private final InfoRepository repository;
    private final UserSession session;

    // Callback riêng cho Android (an toàn hơn Consumer)
    public interface OnUserLoaded {
        void onSuccess(TaiKhoanInfo user);
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
        TaiKhoanInfo cachedUser = session.getUser();
        if (cachedUser != null) {
            callback.onSuccess(cachedUser);
            return;
        }

        // 🔹 Gọi API
        repository.fetchTaiKhoan(userId, new Callback<TaiKhoanInfo>() {
            @Override
            public void onResponse(Call<TaiKhoanInfo> call,
                                   Response<TaiKhoanInfo> response) {

                if (response.isSuccessful() && response.body() != null) {
                    TaiKhoanInfo user = response.body();
                    session.saveUser(user);
                    callback.onSuccess(user);
                } else {
                    callback.onError("Không lấy được thông tin tài khoản");
                }
            }

            @Override
            public void onFailure(Call<TaiKhoanInfo> call, Throwable t) {
                Log.e("InfoManager", "API lỗi", t);
                callback.onError("Lỗi kết nối server");
            }
        });
    }

    public TaiKhoanInfo getCurrentUser() {
        return session.getUser();
    }

    public void logout() {
        session.clear();
    }
}
