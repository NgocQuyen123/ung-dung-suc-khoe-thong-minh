package admin.example.ungdungsuckhoethongminh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import admin.example.ungdungsuckhoethongminh.CaloActivity;
import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.StepsActivity;

public class HealthFragment extends Fragment {

    public HealthFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_health, container, false);

        // Lấy TextView
        TextView btnConnect = view.findViewById(R.id.btnConnect);

        // Set OnClickListener
        btnConnect.setOnClickListener(v -> {
            // Mở StepsActivity
            Intent intent = new Intent(getActivity(), StepsActivity.class);
            startActivity(intent);
        });

        // Lấy TextView
        TextView btnConnectKcal = view.findViewById(R.id.btnConnectKcal);

        // Set OnClickListener
        btnConnectKcal.setOnClickListener(v -> {
            // Mở StepsActivity
            Intent intent = new Intent(getActivity(), CaloActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
