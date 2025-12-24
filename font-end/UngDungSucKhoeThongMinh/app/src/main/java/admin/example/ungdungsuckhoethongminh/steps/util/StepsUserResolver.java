package admin.example.ungdungsuckhoethongminh.steps.util;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import admin.example.ungdungsuckhoethongminh.steps.session.StepsUserSession;

public final class StepsUserResolver {

    private StepsUserResolver() {}

    public static int resolveIdTaiKhoan(@NonNull Context context, @Nullable Bundle args) {
        if (args != null && args.containsKey("idTaiKhoan")) {
            return args.getInt("idTaiKhoan", 1);
        }
        return StepsUserSession.getIdTaiKhoan(context);
    }
}
