package admin.example.ungdungsuckhoethongminh.fragments.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeActivity;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeGetBedActivity;
import admin.example.ungdungsuckhoethongminh.activity.sleep.SleepTimeGetUpActivity;

public class SleepYearFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sleep_year, container, false);

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
}