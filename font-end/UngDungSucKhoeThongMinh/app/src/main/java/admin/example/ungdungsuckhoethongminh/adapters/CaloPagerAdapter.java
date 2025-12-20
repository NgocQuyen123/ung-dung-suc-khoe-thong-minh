package admin.example.ungdungsuckhoethongminh.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import admin.example.ungdungsuckhoethongminh.fragments.calo.CaloDayFragment;
import admin.example.ungdungsuckhoethongminh.fragments.calo.CaloMonthFragment;
import admin.example.ungdungsuckhoethongminh.fragments.calo.CaloWeekFragment;
import admin.example.ungdungsuckhoethongminh.fragments.calo.CaloYearFragment;

public class CaloPagerAdapter extends FragmentStateAdapter {
    public CaloPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new CaloDayFragment();
            case 1: return new CaloWeekFragment();
            case 2: return new CaloMonthFragment();
            case 3: return new CaloYearFragment();
            default: return new CaloDayFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
