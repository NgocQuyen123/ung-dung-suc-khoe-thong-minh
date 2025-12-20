package admin.example.ungdungsuckhoethongminh.fragments.sleep;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeActivity;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeGetBedActivity;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeGetUpActivity;

public class SleepMonthFragment extends Fragment {
    private LinearLayout row1, row2, row3, row4, row5;
    private Calendar calendar;
    private TextView selectedDayView = null;

    public SleepMonthFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sleep_month, container, false);

        // Ánh xạ đúng theo XML bạn gửi
        row1 = view.findViewById(R.id.row1);
        row2 = view.findViewById(R.id.row2);
        row3 = view.findViewById(R.id.row3);
        row4 = view.findViewById(R.id.row4);
        row5 = view.findViewById(R.id.row5);


        calendar = Calendar.getInstance();

        renderCalendar();
        // Bắt sự kiện click vào card "Thời gian ngủ"
        LinearLayout cardSleep = view.findViewById(R.id.cardSleepDuration);
        cardSleep.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SleepTimeActivity.class);
            startActivity(intent);
        });

        // Bắt sự kiện click vào card "Giờ ngủ"
        LinearLayout cardSleepBed = view.findViewById(R.id.cardSleepBed);
        cardSleepBed.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SleepTimeGetBedActivity.class);
            startActivity(intent);
        });

        // Bắt sự kiện click vào card "Giờ thức dậy"
        LinearLayout cardSleepUp = view.findViewById(R.id.cardSleepUp);
        cardSleepUp.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SleepTimeGetUpActivity.class);
            startActivity(intent);
        });
        return view;
    }

    private void renderCalendar() {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        List<LinearLayout> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);

        // Xóa cũ
        for (LinearLayout row : rows) {
            row.removeAllViews();
        }

        Calendar tempCal = Calendar.getInstance();
        tempCal.set(year, month, 1);

        int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1; // Chủ nhật = 1

        if (startDayOfWeek == 0) startDayOfWeek = 7; // Đưa về thứ 2 = 1

        int day = 1;

        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (int i = 0; i < 5; i++) {
            LinearLayout row = rows.get(i);

            for (int col = 1; col <= 7; col++) {

                TextView dayView = (TextView) inflater.inflate(R.layout.item_month_circle, row, false);

                // Trước ngày 1 → để trống
                if (i == 0 && col < startDayOfWeek) {
                    dayView.setText("");
                    row.addView(dayView);
                    continue;
                }

                if (day > daysInMonth) {
                    dayView.setText("");
                    row.addView(dayView);
                    continue;
                }

                dayView.setText(String.valueOf(day));

                // ---- Đánh dấu hôm nay ----
                Calendar now = Calendar.getInstance();
                int today = now.get(Calendar.DAY_OF_MONTH);
                int thisMonth = now.get(Calendar.MONTH);
                int thisYear = now.get(Calendar.YEAR);

                if (day == today && month == thisMonth && year == thisYear) {
                    dayView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_today));
                    dayView.setTypeface(Typeface.DEFAULT_BOLD);
                }

                // ---- Click chọn ngày ----
                int finalDay = day;
                dayView.setOnClickListener(v -> onDaySelected((TextView) v));

                row.addView(dayView);
                day++;
            }
        }
    }

    private void onDaySelected(TextView dayView) {
        // Bỏ chọn cũ
        if (selectedDayView != null) {
            selectedDayView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_normal));
            selectedDayView.setTextColor(Color.BLACK);
            selectedDayView.setTypeface(Typeface.DEFAULT);
        }

        // Chọn mới
        dayView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_selected));
        dayView.setTextColor(Color.WHITE);
        dayView.setTypeface(Typeface.DEFAULT_BOLD);

        selectedDayView = dayView;
    }
}