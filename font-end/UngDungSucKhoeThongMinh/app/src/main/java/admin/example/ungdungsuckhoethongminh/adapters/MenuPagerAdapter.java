package admin.example.ungdungsuckhoethongminh.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import admin.example.ungdungsuckhoethongminh.fragments.menu.MenuHealthFragment;
import admin.example.ungdungsuckhoethongminh.fragments.menu.MenuHomeFragment;

public class MenuPagerAdapter extends FragmentStateAdapter {

    public MenuPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    @NonNull
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MenuHomeFragment();
            case 1:
                return new MenuHealthFragment();
            default:
                return new MenuHomeFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 2;  // số tab
    }
}
