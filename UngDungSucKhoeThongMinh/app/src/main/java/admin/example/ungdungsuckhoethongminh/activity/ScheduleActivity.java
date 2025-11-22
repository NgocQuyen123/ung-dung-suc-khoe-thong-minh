package admin.example.ungdungsuckhoethongminh.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.adapters.WeekDayAdapter;
import admin.example.ungdungsuckhoethongminh.model.WeekDay;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView rvWeekDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ⚠️ CHỌN ĐÚNG LAYOUT
        setContentView(R.layout.activity_demo_schedule_list);

        rvWeekDays = findViewById(R.id.rvWeekDays);

        rvWeekDays.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        rvWeekDays.setAdapter(new WeekDayAdapter(this, getWeekDays()));
    }

    // ⭐ Chỉ Thứ 2 → Thứ 6
    private List<WeekDay> getWeekDays() {
        List<WeekDay> list = new ArrayList<>();

        String[] names = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6"};

        LocalDate today = LocalDate.now();
        int startDay = today.getDayOfMonth();

        for (int i = 0; i < 5; i++) {
            list.add(new WeekDay(names[i], startDay + i));
        }

        return list;
    }
}
