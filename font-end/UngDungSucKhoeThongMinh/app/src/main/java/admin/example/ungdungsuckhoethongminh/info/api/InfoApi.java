package admin.example.ungdungsuckhoethongminh.info.api;


import admin.example.ungdungsuckhoethongminh.model.TaiKhoanInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InfoApi {

    // GET http://10.0.2.2:8081/api/taikhoan/{id}
    @GET("api/taikhoan/{id}")
    Call<TaiKhoanInfo> getTaiKhoan(@Path("id") int id);
}
