package admin.example.ungdungsuckhoethongminh.activity.plan;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.PlanPagerAdapter;
import admin.example.ungdungsuckhoethongminh.adapters.PlanScheduleAdapter;
import admin.example.ungdungsuckhoethongminh.model.PlanScheduleModel;
import admin.example.ungdungsuckhoethongminh.model.WeekDayModel;

public class PlanScheduleListActivity extends AppCompatActivity {

    private RecyclerView rvWeekDays, rvSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_schedule_list);

        // WEEK DAYS
        rvWeekDays = findViewById(R.id.rvWeekDays);
        rvWeekDays.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        rvWeekDays.setAdapter(new PlanPagerAdapter(this, getWeekDays()));

        // SCHEDULE LIST
        rvSchedule = findViewById(R.id.rvSchedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(this));
        rvSchedule.setAdapter(new PlanScheduleAdapter(getScheduleData()));

        FloatingActionButton fabAddSchedule = findViewById(R.id.fabAddSchedule);

        fabAddSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(PlanScheduleListActivity.this, PlanScheduleAdd.class);
            startActivity(intent);
        });

    }

    // Dữ liệu test cho lịch tập
    private List<PlanScheduleModel> getScheduleData() {
        List<PlanScheduleModel> list = new ArrayList<>();

        list.add(new PlanScheduleModel("07:00 AM", "Tập bụng", 65));
        list.add(new PlanScheduleModel("09:30 AM", "Cardio 20 phút", 40));
        list.add(new PlanScheduleModel("03:00 PM", "Tập chân", 80));
        list.add(new PlanScheduleModel("06:00 PM", "Yoga thư giãn", 20));

        return list;
    }

    // Dữ liệu cho ngày trong tuần
    private List<WeekDayModel> getWeekDays() {
        List<WeekDayModel> list = new ArrayList<>();

        list.add(new WeekDayModel("T2", 1));
        list.add(new WeekDayModel("T3", 2));
        list.add(new WeekDayModel("T4", 3));
        list.add(new WeekDayModel("T5", 4));
        list.add(new WeekDayModel("T6", 5));

        return list;
    }
}
