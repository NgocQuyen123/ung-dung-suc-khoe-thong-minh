package admin.example.ungdungsuckhoethongminh.fragments.calo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import admin.example.ungdungsuckhoethongminh.R;

public class CaloWeekFragment extends Fragment {
    public CaloWeekFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calo_week, container, false);

        return view;
    }
}
