package admin.example.ungdungsuckhoethongminh;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CalosPagerAdapter extends FragmentStateAdapter {
    public CalosPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new HealthDayFragment();
            case 1: return new HealthWeekFragment();
            case 2: return new HealthMonthFragment();
            case 3: return new HealthYearFragment();
            default: return new HealthDayFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
