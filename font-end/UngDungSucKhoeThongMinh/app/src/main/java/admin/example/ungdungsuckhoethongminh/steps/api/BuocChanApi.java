package admin.example.ungdungsuckhoethongminh.steps.api;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.steps.model.BuocChanNgayPoint;
import admin.example.ungdungsuckhoethongminh.steps.model.BuocChanThangPoint;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Backend: GET /api/buocchan/{idTaiKhoan}?loai=...
 */
public interface BuocChanApi {

    @GET("api/buocchan/{idTaiKhoan}")
    Call<List<BuocChanNgayPoint>> getNgay(
            @Path("idTaiKhoan") int idTaiKhoan,
            @Query("loai") String loai,
            @Query("ngay") String ngay
    );

    @GET("api/buocchan/{idTaiKhoan}")
    Call<List<BuocChanNgayPoint>> getTuan(
            @Path("idTaiKhoan") int idTaiKhoan,
            @Query("loai") String loai,
            @Query("ngay") String ngay
    );

    @GET("api/buocchan/{idTaiKhoan}")
    Call<List<BuocChanNgayPoint>> getThang(
            @Path("idTaiKhoan") int idTaiKhoan,
            @Query("loai") String loai,
            @Query("nam") int nam,
            @Query("thang") int thang
    );

    @GET("api/buocchan/{idTaiKhoan}")
    Call<List<BuocChanThangPoint>> getNam(
            @Path("idTaiKhoan") int idTaiKhoan,
            @Query("loai") String loai,
            @Query("nam") int nam
    );
}
