package admin.example.ungdungsuckhoethongminh.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import admin.example.ungdungsuckhoethongminh.fragments.step.StepWeekFragment;
import admin.example.ungdungsuckhoethongminh.fragments.step.StepYearFragment;
import admin.example.ungdungsuckhoethongminh.fragments.step.StepDayFragment;
import admin.example.ungdungsuckhoethongminh.fragments.step.StepMonthFragment;

public class StepsPagerAdapter extends FragmentStateAdapter {

    public StepsPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new StepDayFragment();
            case 1: return new StepWeekFragment();
            case 2: return new StepMonthFragment();
            case 3: return new StepYearFragment();
            default: return new StepDayFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
