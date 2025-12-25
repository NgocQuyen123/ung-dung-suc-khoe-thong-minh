package admin.example.ungdungsuckhoethongminh.info.api;

import admin.example.ungdungsuckhoethongminh.model.TaiKhoanInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InfoApi {

    // GET /api/taikhoan/{id}
    @GET("api/taikhoan/{id}")
    Call<TaiKhoanInfo> getTaiKhoan(@Path("id") int id);

    // PUT /api/taikhoan/
    @PUT("api/taikhoan/")
    Call<TaiKhoanInfo> updateTaiKhoan(
            @Body TaiKhoanInfo body
    );
}
