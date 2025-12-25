package admin.example.ungdungsuckhoethongminh.info.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import admin.example.ungdungsuckhoethongminh.model.TaiKhoan;

public class UserSession {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_USER = "tai_khoan_json";

    private SharedPreferences prefs;

    public UserSession(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUser(TaiKhoan user) {
        String json = new Gson().toJson(user);
        prefs.edit().putString(KEY_USER, json).apply();
    }

    public TaiKhoan getUser() {
        String json = prefs.getString(KEY_USER, null);
        if (json == null) return null;
        return new Gson().fromJson(json, TaiKhoan.class);
    }

    public Integer getUserId() {
        TaiKhoan u = getUser();
        return u != null ? u.getId() : null;
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
