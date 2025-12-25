package admin.example.ungdungsuckhoethongminh.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    private static Retrofit getRetrofit() {
        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.100.130:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    // Tiện ích trả về API
    public static TaiKhoanApi getTaiKhoanApi() {
        return getRetrofit().create(TaiKhoanApi.class);
    }


    // Hàm này cũng tương tự, nhưng dùng tên thống nhất
    public static TaiKhoanApi getApiService() {
        return getRetrofit().create(TaiKhoanApi.class);
    }


// ================= API CÂN NẶNG =================
    public static CanNangApi getCanNangApi() {
        return ApiClient.getRetrofit().create(CanNangApi.class);
    }
}
