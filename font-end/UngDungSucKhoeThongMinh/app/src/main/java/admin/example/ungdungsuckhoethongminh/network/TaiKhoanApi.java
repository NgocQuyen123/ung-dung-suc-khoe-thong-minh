package admin.example.ungdungsuckhoethongminh.network;
import admin.example.ungdungsuckhoethongminh.DTO.CreateTaiKhoanRequest;

import admin.example.ungdungsuckhoethongminh.DTO.LoginRequest;
import admin.example.ungdungsuckhoethongminh.DTO.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TaiKhoanApi {
    @POST("/api/taikhoan")
    Call<Void> createTaiKhoan(@Body CreateTaiKhoanRequest request);

    @POST("/api/taikhoan/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}