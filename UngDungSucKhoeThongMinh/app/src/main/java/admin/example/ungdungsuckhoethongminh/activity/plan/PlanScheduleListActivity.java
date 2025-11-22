package admin.example.ungdungsuckhoethongminh.activity.plan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.PlanPagerAdapter;
import admin.example.ungdungsuckhoethongminh.model.WeekDayModel;

public class PlanScheduleListActivity extends AppCompatActivity {

    private RecyclerView rvWeekDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ⚠️ CHỌN ĐÚNG LAYOUT
        setContentView(R.layout.activity_plan_schedule_list);

        rvWeekDays = findViewById(R.id.rvWeekDays);

        rvWeekDays.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        rvWeekDays.setAdapter(new PlanPagerAdapter(this, getWeekDays()));
    }

    // ⭐ Chỉ Thứ 2 → Thứ 6
    private List<WeekDayModel> getWeekDays() {
        List<WeekDayModel> list = new ArrayList<>();

        String[] names = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6"};

        LocalDate today = LocalDate.now();
        int startDay = today.getDayOfMonth();

        for (int i = 0; i < 5; i++) {
            list.add(new WeekDayModel(names[i], startDay + i));
        }

        return list;
    }
}
