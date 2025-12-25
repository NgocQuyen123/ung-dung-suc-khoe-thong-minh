package admin.example.ungdungsuckhoethongminh.steps.api;

/**
 * Central place to configure backend base URL for Steps APIs.
 *
 * Notes:
 * - Android Emulator -> use http://10.0.2.2:8080/
 * - Physical device   -> use http://<your-lan-ip>:8080/
 */
public final class StepsApiConfig {

    private StepsApiConfig() {}

    /**
     * Change this value depending on how you run the backend.
     */
    public static final String BASE_URL = "http://192.168.1.171:8080/";
}
