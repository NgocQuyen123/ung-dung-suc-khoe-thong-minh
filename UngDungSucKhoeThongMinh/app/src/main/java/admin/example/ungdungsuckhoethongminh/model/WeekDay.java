package admin.example.ungdungsuckhoethongminh.model;

public class WeekDay {
    private String name;
    private int dayNumber;

    public WeekDay(String name, int dayNumber) {
        this.name = name;
        this.dayNumber = dayNumber;
    }

    public String getName() {
        return name;
    }

    public int getDayNumber() {
        return dayNumber;
    }
}
