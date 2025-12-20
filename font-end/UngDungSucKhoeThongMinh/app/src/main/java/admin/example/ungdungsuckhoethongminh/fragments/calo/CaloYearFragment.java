package admin.example.ungdungsuckhoethongminh.fragments.calo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import admin.example.ungdungsuckhoethongminh.R;

public class CaloYearFragment extends Fragment {
    private LinearLayout columnContainer;

    public CaloYearFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calo_year, container, false);

        // lấy container từ XML
        columnContainer = view.findViewById(R.id.columnContainer);

        // tạo 12 cột
        columnContainer.post(() -> generateColumns(12));

        return view;
    }

    private void generateColumns(int count) {
        if (getContext() == null) return;

        int containerWidth = columnContainer.getWidth();
        int columnWidth = containerWidth / count;


        for (int i = 1; i <= count; i++) {

            // layout dọc cho 1 cột
            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(LinearLayout.VERTICAL);
            col.setGravity(Gravity.CENTER_HORIZONTAL);

            LinearLayout.LayoutParams colParams = new LinearLayout.LayoutParams(
                    columnWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            col.setLayoutParams(colParams);

            // số phía trên (1 -> 12)
            TextView number = new TextView(getContext());
            number.setText(String.valueOf(i)); // <-- ĐÁNH SỐ 1 - 12
            number.setTextSize(18);
            number.setTypeface(Typeface.DEFAULT_BOLD);
            number.setGravity(Gravity.CENTER);
            number.setPadding(0, 0, 0, dp(10));

            // đường thẳng
            View line = new View(getContext());
            LinearLayout.LayoutParams lineParams =
                    new LinearLayout.LayoutParams(dp(2), 0, 1f);
            line.setLayoutParams(lineParams);
            line.setBackgroundColor(Color.parseColor("#BDBDBD"));

            col.addView(number);
            col.addView(line);

            columnContainer.addView(col);
        }
    }


    private int dp(int dp) {
        if (getContext() == null) return dp;
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
