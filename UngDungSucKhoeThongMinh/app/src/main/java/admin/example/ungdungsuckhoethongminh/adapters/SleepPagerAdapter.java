package admin.example.ungdungsuckhoethongminh.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import admin.example.ungdungsuckhoethongminh.fragments.sleep.SleepDayFragment;
import admin.example.ungdungsuckhoethongminh.fragments.sleep.SleepMonthFragment;
import admin.example.ungdungsuckhoethongminh.fragments.sleep.SleepWeekFragment;
import admin.example.ungdungsuckhoethongminh.fragments.sleep.SleepYearFragment;

public class SleepPagerAdapter extends FragmentStateAdapter {

    public SleepPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new SleepDayFragment();
            case 1: return new SleepWeekFragment();
            case 2: return new SleepMonthFragment();
            case 3: return new SleepYearFragment();
            default: return new SleepDayFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}