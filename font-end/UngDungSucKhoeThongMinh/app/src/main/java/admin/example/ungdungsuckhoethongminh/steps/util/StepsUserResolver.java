package admin.example.ungdungsuckhoethongminh.steps.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import admin.example.ungdungsuckhoethongminh.steps.session.StepsUserSession;

public final class StepsUserResolver {

    // Constructor private để tránh việc khởi tạo đối tượng
    private StepsUserResolver() {}

    // Phương thức tĩnh resolveIdTaiKhoan
    public static int resolveIdTaiKhoan(@NonNull Context context, @Nullable Bundle args) {
        // Lấy userId từ SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("MyAppData", Context.MODE_PRIVATE);
        int userId = prefs.getInt("userId", 1);  // Lấy userId từ SharedPreferences, mặc định là 1 nếu không tìm thấy

        // Kiểm tra nếu args chứa key "idTaiKhoan", lấy giá trị từ args
        if (args != null && args.containsKey("idTaiKhoan")) {
            return args.getInt("idTaiKhoan", userId);  // Trả về giá trị trong args nếu có
        }

        // Nếu không có, trả về userId từ StepsUserSession (có thể được lưu từ một session trước)
        return StepsUserSession.getIdTaiKhoan(context);
    }
}
