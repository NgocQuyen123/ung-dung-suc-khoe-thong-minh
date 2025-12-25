package admin.example.ungdungsuckhoethongminh.network;

import admin.example.ungdungsuckhoethongminh.model.OtpResponse;
import admin.example.ungdungsuckhoethongminh.model.SendOtpRequest;
import admin.example.ungdungsuckhoethongminh.model.VerifyOtpRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/api/auth/send-otp")
    Call<OtpResponse> sendOtp(@Body SendOtpRequest request);

    @POST("/api/auth/verify-otp")
    Call<Boolean> verifyOtp(@Body VerifyOtpRequest request);
}
