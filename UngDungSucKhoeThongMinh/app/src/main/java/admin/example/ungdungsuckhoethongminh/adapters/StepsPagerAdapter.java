package admin.example.ungdungsuckhoethongminh.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import admin.example.ungdungsuckhoethongminh.fragments.WeekFragment;
import admin.example.ungdungsuckhoethongminh.fragments.YearFragment;
import admin.example.ungdungsuckhoethongminh.fragments.DayFragment;
import admin.example.ungdungsuckhoethongminh.fragments.MonthFragment;

public class StepsPagerAdapter extends FragmentStateAdapter {

    public StepsPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new DayFragment();
            case 1: return new WeekFragment();
            case 2: return new MonthFragment();
            case 3: return new YearFragment();
            default: return new DayFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
