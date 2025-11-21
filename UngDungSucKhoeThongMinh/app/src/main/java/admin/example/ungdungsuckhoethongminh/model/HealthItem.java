package admin.example.ungdungsuckhoethongminh.model;

public class HealthItem {
    private String title;
    private String subtitle;
    private int iconRes; // drawable resource

    public HealthItem(String title, String subtitle, int iconRes) {
        this.title = title;
        this.subtitle = subtitle;
        this.iconRes = iconRes;
    }

    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public int getIconRes() { return iconRes; }
}
