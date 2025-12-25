package admin.example.ungdungsuckhoethongminh.info.api;

import admin.example.ungdungsuckhoethongminh.model.TaiKhoan;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InfoApi {

    @GET("api/taikhoan/{id}")
    Call<TaiKhoan> getTaiKhoan(@Path("id") int id);

    @PUT("api/taikhoan/")
    Call<TaiKhoan> updateTaiKhoan(@Body TaiKhoan body);
}
