package admin.example.ungdungsuckhoethongminh.steps.session;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

/**
 * Stores/retrieves the current TaiKhoan id used for Steps API calls.
 *
 * This keeps Steps feature independent from the rest of the app login flow.
 */
public final class StepsUserSession {

    private static final String PREF_NAME = "steps_session";
    private static final String KEY_ID_TAI_KHOAN = "idTaiKhoan";

    private StepsUserSession() {}

    public static void setIdTaiKhoan(@NonNull Context context, int idTaiKhoan) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(KEY_ID_TAI_KHOAN, idTaiKhoan).apply();
    }

    /**
     * @return stored idTaiKhoan, or 1 if not set yet.
     */
    public static int getIdTaiKhoan(@NonNull Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getInt(KEY_ID_TAI_KHOAN, 1);
    }
}
