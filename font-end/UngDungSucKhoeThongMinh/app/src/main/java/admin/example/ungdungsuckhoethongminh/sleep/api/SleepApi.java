package admin.example.ungdungsuckhoethongminh.sleep.api;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.sleep.model.SleepNgayPoint;
import admin.example.ungdungsuckhoethongminh.sleep.model.SleepThangPoint;
import admin.example.ungdungsuckhoethongminh.sleep.model.SleepThangAvg;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Backend: GET /api/sleep/{idTaiKhoan}?loai=...
 */
public interface SleepApi {

    @GET("api/sleep/{idTaiKhoan}")
    Call<List<SleepNgayPoint>> getNgay(
            @Path("idTaiKhoan") int idTaiKhoan,
            @Query("loai") String loai,
            @Query("ngay") String ngay
    );

    @GET("api/sleep/{idTaiKhoan}")
    Call<List<SleepNgayPoint>> getTuan(
            @Path("idTaiKhoan") int idTaiKhoan,
            @Query("loai") String loai,
            @Query("ngay") String ngay
    );

    @GET("api/sleep/{idTaiKhoan}")
    Call<List<SleepNgayPoint>> getThang(
            @Path("idTaiKhoan") int idTaiKhoan,
            @Query("loai") String loai,
            @Query("nam") int nam,
            @Query("thang") int thang
    );

    @GET("api/sleep/{idTaiKhoan}")
    Call<List<SleepThangAvg>> getNam(
            @Path("idTaiKhoan") int idTaiKhoan,
            @Query("loai") String loai,
            @Query("nam") int nam
    );

    /** Legacy totals (loai=nam_tong|namtong|nam_tonghop) */
    @GET("api/sleep/{idTaiKhoan}")
    Call<List<SleepThangPoint>> getNamTong(
            @Path("idTaiKhoan") int idTaiKhoan,
            @Query("loai") String loai,
            @Query("nam") int nam
    );
}
