package admin.example.ungdungsuckhoethongminh.info.repository;

import admin.example.ungdungsuckhoethongminh.info.api.InfoApi;
import admin.example.ungdungsuckhoethongminh.info.api.InfoApiClient;
import admin.example.ungdungsuckhoethongminh.model.TaiKhoanInfo;
import retrofit2.Callback;

public class InfoRepository {

    private final InfoApi api;

    public InfoRepository() {
        api = InfoApiClient.getClient().create(InfoApi.class);
    }

    // ===== GET =====
    public void fetchTaiKhoan(int id, Callback<TaiKhoanInfo> callback) {
        api.getTaiKhoan(id).enqueue(callback);
    }

    // ===== PUT UPDATE =====
    public void updateTaiKhoan(
            TaiKhoanInfo body,
            Callback<TaiKhoanInfo> callback) {

        api.updateTaiKhoan(body).enqueue(callback);
    }
}
