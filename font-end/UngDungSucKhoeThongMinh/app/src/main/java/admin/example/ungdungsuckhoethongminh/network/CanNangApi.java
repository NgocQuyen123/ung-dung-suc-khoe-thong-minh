package admin.example.ungdungsuckhoethongminh.network;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.model.CanNangHienTaiRequest;
import admin.example.ungdungsuckhoethongminh.model.CanNangHienTaiResponse;
import admin.example.ungdungsuckhoethongminh.model.CanNangNamModel;
import admin.example.ungdungsuckhoethongminh.model.CanNangThangModel;
import admin.example.ungdungsuckhoethongminh.model.CanNangTuanModel;
import admin.example.ungdungsuckhoethongminh.model.MucDoHoatDongModel;
import admin.example.ungdungsuckhoethongminh.model.NhipDoCanNangModel;
import admin.example.ungdungsuckhoethongminh.model.TaiKhoanModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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
    @GET("api/muc-do-hoat-dong")
    Call<List<MucDoHoatDongModel>> getAllMucDoHoatDong();

    @GET("api/nhip-do-can-nang")
    Call<List<NhipDoCanNangModel>> getAllNhipDoCanNang();

    @GET("api/taikhoan")
    Call<List<TaiKhoanModel>> getAllTaiKhoan();

    @GET("api/taikhoan/{id}/can-nang-hien-tai")
    Call<CanNangHienTaiResponse> getCanNangHienTai(@Path("id") int id);

    @PUT("api/taikhoan/{id}/can-nang-hien-tai")
    Call<CanNangHienTaiResponse> updateCanNangHienTai(@Path("id") int id,
                                                      @Body CanNangHienTaiRequest req);
}
