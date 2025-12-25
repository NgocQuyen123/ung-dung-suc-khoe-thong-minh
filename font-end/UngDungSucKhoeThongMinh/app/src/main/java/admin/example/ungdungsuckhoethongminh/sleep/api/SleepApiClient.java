package admin.example.ungdungsuckhoethongminh.sleep.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class SleepApiClient {

    private static volatile Retrofit retrofit;

    private SleepApiClient() {}

    public static Retrofit get() {
        if (retrofit == null) {
            synchronized (SleepApiClient.class) {
                if (retrofit == null) {
                    HttpLoggingInterceptor log = new HttpLoggingInterceptor();
                    log.setLevel(HttpLoggingInterceptor.Level.BODY);

                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(log)
                            .build();

                    Gson gson = new GsonBuilder().create();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(SleepApiConfig.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build();
                }
            }
        }
        return retrofit;
    }
}
