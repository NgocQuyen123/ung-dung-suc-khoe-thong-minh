package admin.example.ungdungsuckhoethongminh.data;

import android.content.Context;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import admin.example.ungdungsuckhoethongminh.model.AppData;

public class Repository {
    public interface Callback {
        void onLoaded(AppData data);
        void onError(Exception e);
    }

    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public void loadFromAssets(final Context ctx, final Callback cb) {
        exec.submit(() -> {
            try {
                InputStream is = ctx.getAssets().open("db.json");
                InputStreamReader reader = new InputStreamReader(is);
                AppData data = new Gson().fromJson(reader, AppData.class);
                reader.close();
                // post back to main thread
                android.os.Handler h = new android.os.Handler(android.os.Looper.getMainLooper());
                h.post(() -> cb.onLoaded(data));
            } catch (Exception e) {
                android.os.Handler h = new android.os.Handler(android.os.Looper.getMainLooper());
                h.post(() -> cb.onError(e));
            }
        });
    }
}
