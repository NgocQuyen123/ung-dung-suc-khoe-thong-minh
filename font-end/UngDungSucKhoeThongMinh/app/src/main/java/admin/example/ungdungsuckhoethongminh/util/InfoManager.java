package admin.example.ungdungsuckhoethongminh.util;

import android.content.Context;

import android.util.Log;
import java.util.function.Consumer;

import admin.example.ungdungsuckhoethongminh.model.TaiKhoanInfo;
import admin.example.ungdungsuckhoethongminh.repository.InfoRepository;
import admin.example.ungdungsuckhoethongminh.session.UserSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoManager {

    private InfoRepository repository;
    private UserSession session;

    public InfoManager(Context context) {
        repository = new InfoRepository();
        session = new UserSession(context);
    }

    // Lấy từ session trước, nếu chưa có thì gọi API
    public void loadUser(int id, Consumer<TaiKhoanInfo> onDone) {

        TaiKhoanInfo cached = session.getUser();
        if (cached != null) {
            onDone.accept(cached);
            return;
        }

        repository.fetchTaiKhoan(id, new Callback<TaiKhoanInfo>() {
            @Override
            public void onResponse(Call<TaiKhoanInfo> call,
                                   Response<TaiKhoanInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    session.saveUser(response.body());
                    onDone.accept(response.body());
                }
            }

            @Override
            public void onFailure(Call<TaiKhoanInfo> call, Throwable t) {
                Log.e("INFO_MANAGER", "Load user failed", t);
            }
        });
    }

    public TaiKhoanInfo getCurrentUser() {
        return session.getUser();
    }
}
