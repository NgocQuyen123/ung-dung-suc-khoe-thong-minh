package admin.example.ungdungsuckhoethongminh.network;

import admin.example.ungdungsuckhoethongminh.model.CanNangNamModel;
import admin.example.ungdungsuckhoethongminh.model.CanNangThangModel;
import admin.example.ungdungsuckhoethongminh.model.CanNangTuanModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CanNangApi {
    @GET("api/cannang/tuan")
    Call<CanNangTuanModel> layCanNangTheoTuan(@Query("date") String date);

    @GET("api/cannang/thang")
    Call<CanNangThangModel> layCanNangTheoThang(
            @Query("thang") int thang,
            @Query("nam") int nam
    );

    @GET("api/cannang/nam")
    Call<CanNangNamModel> layCanNangTheoNam(@Query("nam") int nam);
}
