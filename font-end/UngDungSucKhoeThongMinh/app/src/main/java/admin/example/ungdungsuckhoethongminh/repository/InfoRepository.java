package admin.example.ungdungsuckhoethongminh.repository;

import admin.example.ungdungsuckhoethongminh.api.InfoApi;
import admin.example.ungdungsuckhoethongminh.api.InfoApiClient;
import admin.example.ungdungsuckhoethongminh.model.TaiKhoanInfo;

import retrofit2.Callback;

public class InfoRepository {

    private final InfoApi api;

    public InfoRepository() {
        api = InfoApiClient.getClient().create(InfoApi.class);
    }

    public void fetchTaiKhoan(int id, Callback<TaiKhoanInfo> callback) {
        api.getTaiKhoan(id).enqueue(callback);
    }
}
