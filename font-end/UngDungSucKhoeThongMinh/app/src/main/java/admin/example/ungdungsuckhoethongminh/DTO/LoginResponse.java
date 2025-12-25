package admin.example.ungdungsuckhoethongminh.DTO;
import admin.example.ungdungsuckhoethongminh.model.TaiKhoan;
public class LoginResponse {
    private boolean success;
    private String message;
    private TaiKhoan taiKhoan;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public TaiKhoan getTaiKhoan() { return taiKhoan; }
}