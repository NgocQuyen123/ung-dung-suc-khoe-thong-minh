package admin.example.ungdungsuckhoethongminh.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import admin.example.ungdungsuckhoethongminh.fragments.step.StepWeekFragment;
import admin.example.ungdungsuckhoethongminh.fragments.step.StepYearFragment;
import admin.example.ungdungsuckhoethongminh.fragments.step.StepDayFragment;
import admin.example.ungdungsuckhoethongminh.fragments.step.StepMonthFragment;

public class StepsPagerAdapter extends FragmentStateAdapter {

    private final int idTaiKhoan;

    public StepsPagerAdapter(@NonNull FragmentActivity fa, int idTaiKhoan) {
        super(fa);
        this.idTaiKhoan = idTaiKhoan;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new StepDayFragment();
                break;
            case 1:
                fragment = new StepWeekFragment();
                break;
            case 2:
                fragment = new StepMonthFragment();
                break;
            case 3:
                fragment = new StepYearFragment();
                break;
            default:
                fragment = new StepDayFragment();
                break;
        }

        Bundle args = new Bundle();
        args.putInt("idTaiKhoan", idTaiKhoan);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
