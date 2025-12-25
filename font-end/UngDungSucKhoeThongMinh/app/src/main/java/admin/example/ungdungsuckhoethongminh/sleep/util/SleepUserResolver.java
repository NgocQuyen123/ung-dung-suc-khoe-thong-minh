package admin.example.ungdungsuckhoethongminh.sleep.util;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Reuse the same idTaiKhoan resolving strategy as Steps.
 */
public final class SleepUserResolver {

    private SleepUserResolver() {}

    public static int resolveIdTaiKhoan(@NonNull Context context, @Nullable Bundle args) {
        return admin.example.ungdungsuckhoethongminh.steps.util.StepsUserResolver.resolveIdTaiKhoan(context, args);
    }
}
