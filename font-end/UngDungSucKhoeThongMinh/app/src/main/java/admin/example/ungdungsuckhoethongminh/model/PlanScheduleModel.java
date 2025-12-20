package admin.example.ungdungsuckhoethongminh.model;

public class PlanScheduleModel {
    public String time;
    public String workoutName;
    public int progress;

    public PlanScheduleModel(String time, String workoutName, int progress) {
        this.time = time;
        this.workoutName = workoutName;
        this.progress = progress;
    }
}

