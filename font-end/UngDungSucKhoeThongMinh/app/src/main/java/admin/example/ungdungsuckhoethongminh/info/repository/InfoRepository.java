package admin.example.ungdungsuckhoethongminh.info.repository;

import admin.example.ungdungsuckhoethongminh.info.api.InfoApi;
import admin.example.ungdungsuckhoethongminh.info.api.InfoApiClient;
import admin.example.ungdungsuckhoethongminh.model.TaiKhoan;
import retrofit2.Callback;

public class InfoRepository {

    private final InfoApi api;

    public InfoRepository() {
        api = InfoApiClient.getClient().create(InfoApi.class);
    }

    // ===== GET =====
    public void fetchTaiKhoan(int id, Callback<TaiKhoan> callback) {
        api.getTaiKhoan(id).enqueue(callback);
    }

    // ===== PUT UPDATE =====
    public void updateTaiKhoan(
            TaiKhoan body,
            Callback<TaiKhoan> callback) {

        api.updateTaiKhoan(body).enqueue(callback);
    }
}