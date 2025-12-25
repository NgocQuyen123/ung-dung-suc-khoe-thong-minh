package admin.example.ungdungsuckhoethongminh.model;

public class SendOtpRequest {
    private String email;

    public SendOtpRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

